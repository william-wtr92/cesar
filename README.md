# Cesar API - Spring Boot

Web API for managing courses, students, teachers and documents.

# Getting Started

## Prerequisites

- Install Azurite : `npm install -g azurite`
- Start Azurite : `azurite --location ./azurite/data --silent --debug ./azurite/logs/debug.log`
- SMTP credentials like `SendGrid` or `Mailgun`

# Features 

- [x] Register
- [x] Login
- [x] Add a classroom
- [x] Update user classroom
- [x] Add a course
- [ ] Delete a course
- [ ] Edit a course
- [x] Retrieve a course
- [x] List all courses
- [ ] Filter courses by date and program
- [x] Add a student to a course
- [x] Add a teacher to a course
- [x] Start attendance
- [ ] Take attendance
- [x] Add a document
- [x] Download a document
- [x] Send an email to a list of students
- [x] Errors Handling
