Cau truc cac file json
```
//students.json
[
    {
        "id": ,
        "type": ,
        "scores": [
            {
                "courseId": ,
                "midScore": ,
                "finalScore":
            }
        ],
        "registedCourses:" [
            "id", "id", ...
        ],
        "major": ,
        "class": ,
        "year": 
    }
]

//courses.json
[
    {
        "id": ,
        "type: ,
        "midScale": ,
        "finalScale": ,
        "studentsList": [
            "id", "id", ...
        ],
        "prerequisites": [
            "id", "id", ...
        ]
    }
]

// majors.json
[
    {
        "id": ,
        "courses: [
            "id", "id", ...
        ],
        "requiredCredits": 
    }
]

// classes.json
[
    {
        "id": ,
        "courses": [
            [], [], ...
        ]
    }
]
```
User admin
```$xslt
Views:
    - Students
    - Courses
    - Majors
    - Classes
    - Vao` diem?
```
User student
```$xslt
Views:
    - Course registration
    - Xem d
    - Xet tot nghiep
```

