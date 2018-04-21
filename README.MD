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
Hướng dẫn đọc-ghi file để test.
```
commend/uncommend code from : AdminDashboardController.class from "com.project.javafx.controllerfx"
on 2 method. 
    loadDataOnStart()
    saveDataOnClose()
```
Access an object from repository(List)

```
ví dụ từ student "List"
StudentRepository.getInstance().method();

 - Available method:
public T findById(String id);
public Set<T> findAll();
public boolean update(T entity);
public boolean save(T entity);          // add new student
public boolean delete(T entity);
public boolean deleteByID(String id);
```

MongoDB install guide for testing
```
1. Install mongo
https://docs.mongodb.com/manual/administration/install-community/

2. Import our dump data;
cmd/terminal as Admin
navigate to bin: mongdodb\..\bin folder like
cd C:\Program Files\MongoDB\Server\3.6\bin
copy (.json vào folder bin cho dễ)

# import dump file:
mongoimport -d database_name -c collection_name outfile.json

mongoimport -d AppDev -c Classes -o Classes.json
mongoimport -d AppDev -c Courses -o Courses.json
mongoimport -d AppDev -c Majors -o Majors.json
mongoimport -d AppDev -c Students -o Students.json
```

