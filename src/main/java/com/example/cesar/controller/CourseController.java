package com.example.cesar.controller;

import com.example.cesar.dto.Course.AllCoursesByClassroomDto;
import com.example.cesar.dto.Course.CourseCreateDto;
import com.example.cesar.dto.Course.CourseGetSingleDto;
import com.example.cesar.dto.Course.CourseUpdateDto;
import com.example.cesar.entity.Course;
import com.example.cesar.service.CourseService;
import com.example.cesar.utils.constants.RoleConstants;
import com.example.cesar.utils.response.AllCourseResponse;
import com.example.cesar.utils.response.AllCoursesByClassroomResponse;
import com.example.cesar.utils.response.ApiResponse;
import com.example.cesar.utils.response.SingleCourseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("/api/courses")
@Tag(name = "Courses API", description = "Course management")
@SecurityRequirement(name = "Bearer Authentication")
public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @Operation(summary = "Get all courses")
    @PreAuthorize("hasRole('"+ RoleConstants.ROLE_ADMIN +"')")
    @GetMapping
    public ResponseEntity<AllCourseResponse> getAllCourses() {
        List<Course> courses = courseService.getCourses();
        AllCourseResponse response = new AllCourseResponse("Courses successfully fetched", HttpStatus.OK.value(), true, courses);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Get a single course")
    @GetMapping ("/single")
    public ResponseEntity<SingleCourseResponse> getSingleCourse(@Valid @RequestBody CourseGetSingleDto courseGetSingleDto,  @AuthenticationPrincipal UserDetails userDetails) {
        Course course = courseService.getCourse(courseGetSingleDto, userDetails);
        SingleCourseResponse response = new SingleCourseResponse("Course successfully fetched", HttpStatus.OK.value(), true, course);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Create a new course")
    @PreAuthorize("hasRole('"+ RoleConstants.ROLE_ADMIN +"')")
    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createCourse(@Valid @RequestBody CourseCreateDto courseCreateDto) {
        String response = courseService.createCourse(courseCreateDto);
        ApiResponse apiResponse = new ApiResponse(response, HttpStatus.OK.value(), true);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @Operation(summary = "Delete a course")
    @PreAuthorize("hasRole('"+ RoleConstants.ROLE_ADMIN +"')")
    @DeleteMapping("/{courseId}")
    public ResponseEntity<ApiResponse> deleteCourse(@PathVariable Long courseId) {
        String response = courseService.deleteCourse(courseId);
        ApiResponse apiResponse = new ApiResponse(response, HttpStatus.OK.value(), true);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @Operation(summary = "Update a course")
    @PreAuthorize("hasRole('"+ RoleConstants.ROLE_ADMIN +"')")
    @PutMapping("/{courseId}/update")
    public ResponseEntity<ApiResponse> updateCourse(@Valid @RequestBody CourseUpdateDto courseUpdateDto, @PathVariable("courseId") Long courseId) {
        String response = courseService.updateCourse(courseUpdateDto, courseId);
        ApiResponse apiResponse = new ApiResponse(response, HttpStatus.OK.value(), true);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @Operation(summary = "Get courses by classroom")
    @GetMapping("/classroom")
    public ResponseEntity<AllCoursesByClassroomResponse> getCoursesByClassroom(@RequestParam String classroomName, @AuthenticationPrincipal UserDetails userDetails) {
        List<AllCoursesByClassroomDto> courses = courseService.getCoursesByClassroom(classroomName, userDetails);
        AllCoursesByClassroomResponse response = new AllCoursesByClassroomResponse("Courses successfully fetched", HttpStatus.OK.value(), true, courses);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @Operation(summary = "Upload file")
    @PreAuthorize("hasRole('"+ RoleConstants.ROLE_TEACHER +"')")
    @PutMapping("/{id}/upload")
    public ResponseEntity<ApiResponse> uploadFile(@RequestParam("file") MultipartFile file, @PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails){
        String response = courseService.uploadFile(file, id, userDetails);
        ApiResponse apiResponse = new ApiResponse(response, HttpStatus.OK.value(), true);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
