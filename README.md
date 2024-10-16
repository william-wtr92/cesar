# Cesar API - Spring Boot

Web API for managing courses, students, teachers and documents.

# Swagger UI

- [Swagger UI](http://localhost:8080/swagger-ui.html) 

# Getting Started

## Prerequisites

- Install Azurite : `npm install -g azurite`
- Start Azurite : `azurite --location ./azurite/data --silent --debug ./azurite/logs/debug.log`
- SMTP credentials like `SendGrid` or `Mailgun`

- Install K6 : `brew install k6`
- Run K6 : `cd /k6/ && k6 run --compatibility-mode=experimental_enhanced script.ts`

# Features 

- [x] Register
- [x] Login
- [x] Add a classroom
- [x] Update user classroom
- [x] Retrieve students classroom
- [x] Add a course
- [ ] Delete a course
- [ ] Edit a course
- [x] Retrieve a course
- [x] List all courses
- [ ] Filter courses by date and program
- [x] Add a student to a course
- [x] Add a teacher to a course
- [x] Start attendance
- [x] Take attendance
- [x] Get attendance by id
- [x] Automatic put student absent
- [x] Add a document
- [x] Download a document
- [x] Add grade to a student for a specific course
- [x] Update grade
- [x] Delete grade
- [x] Get All grades for a student
- [x] Email a list of students
- [x] Errors Handling
