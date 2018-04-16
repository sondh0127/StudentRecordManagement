```
//students.json
[
    {
        "id": ,
        "type": ,
        "passedCourses": [
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
        "year": ,
        "requiredCredits": 
    }
]

//courses.json
[
    {
        "id": ,
        "type: ,
        "midScale": ,
        "finalScale": ,
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
        ]
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