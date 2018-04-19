package com.project.javafx.ulti.gsonUtil;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.project.javafx.model.CreditMajor;

public class StudentExclusionStrategy implements ExclusionStrategy {
    @Override
    public boolean shouldSkipField(FieldAttributes fieldAttributes) {
        if (fieldAttributes.getDeclaringClass() == CreditMajor.class && !fieldAttributes.getName().equals("majorCode")) {
            return true;
        }
        return false;
    }

    @Override
    public boolean shouldSkipClass(Class<?> aClass) {
        return false;
    }
}
