package com.project.javafx.model;

public enum YearOfStudy {
        FIRST_YEAR("First Year"),
        SECOND_YEAR("Second Year"),
        THIRD_YEAR("Third Year"),
        FOURTH_YEAR("Fourth Year"),
        GRADUATED("Graduated");

        private final String text;

        YearOfStudy(String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }
    }