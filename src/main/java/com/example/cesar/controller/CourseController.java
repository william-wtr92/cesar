package com.example.cesar.controller;

import com.example.cesar.dto.Course.CourseCreateDto;
import com.example.cesar.dto.Course.CourseGetSingleDto;
import com.example.cesar.entity.Course;
import com.example.cesar.service.CourseService;
import com.example.cesar.utils.constants.RoleConstants;
import com.example.cesar.utils.response.AllCourseResponse;
import com.example.cesar.utils.response.ApiResponse;
import com.example.cesar.utils.response.SingleCourseResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@PreAuthorize("hasRole('"+ RoleConstants.ROLE_TEACHER +"')")
@RequestMapping("/api/courses")
public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public ResponseEntity<AllCourseResponse> getAllCourses() {
        List<Course> courses = courseService.getCourses();
        AllCourseResponse response = new AllCourseResponse("Courses successfully fetched", HttpStatus.OK.value(), true, courses);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping ("/single")
    public ResponseEntity<SingleCourseResponse> getSingleCourse(@Valid @RequestBody CourseGetSingleDto courseGetSingleDto) {
        Course course = courseService.getCourse(courseGetSingleDto);
        SingleCourseResponse response = new SingleCourseResponse("Course successfully fetched", HttpStatus.OK.value(), true, course);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createClassroom(@Valid @RequestBody CourseCreateDto courseCreateDto) {
        String response = courseService.createCourse(courseCreateDto);
        ApiResponse apiResponse = new ApiResponse(response, HttpStatus.OK.value(), true);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }


    @PutMapping("/{id}/upload")
    public ResponseEntity<ApiResponse> uploadFile(@RequestParam("file") MultipartFile file, @PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails){
        String response = courseService.uploadFile(file, id, userDetails);
        ApiResponse apiResponse = new ApiResponse(response, HttpStatus.OK.value(), true);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
