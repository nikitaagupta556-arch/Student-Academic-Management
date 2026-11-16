# Student Academic Management REST API

Java 17 + Spring Boot 3.2.5 project using an in-memory `List<Student>` (no database).

## Run in IntelliJ IDEA
1. Open this folder as a Maven project.
2. Set Project SDK to JDK 17.
3. Wait for Maven dependencies to download.
4. Run `StudentApplication.java`.

The API runs at `http://localhost:8080`.

## Endpoints
- `GET /students` — list all students
- `GET /students/{studentId}` — get one student
- `POST /students` — add a student (JSON body)
- `PUT /students/{studentId}` — update a student (JSON body)
- `DELETE /students/{studentId}` — delete a student
- `GET /students/search?branch=CSE&year=2` — search by branch and year
- `GET /students/topper?cgpa=9.0` — students with CGPA greater than or equal to the value

Data is stored in memory and resets when the application restarts.
