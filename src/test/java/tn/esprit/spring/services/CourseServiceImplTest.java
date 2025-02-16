package tn.esprit.spring.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.Support;
import tn.esprit.spring.entities.TypeCourse;
import tn.esprit.spring.repositories.ICourseRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CourseServicesImplTest {

    @Mock
    private ICourseRepository courseRepository; // Mock the repository

    @InjectMocks
    private CourseServicesImpl courseService; // Inject mock into service

    private Course course;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks

        // Sample Course object for tests
        course = new Course(
                1L,                            // numCourse
                3,                             // level
                TypeCourse.COLLECTIVE_ADULT,   // TypeCourse enum
                Support.SKI,                   // Support enum (now using SKI)
                200.0f,                        // price
                2,                             // timeSlot
                null                           // Ignored registrations
        );
    }

    @Test
    void testRetrieveAllCourses() {
        // Arrange: Mock findAll()
        List<Course> mockCourses = Arrays.asList(course);
        when(courseRepository.findAll()).thenReturn(mockCourses);

        // Act: Retrieve courses
        List<Course> result = courseService.retrieveAllCourses();

        // Assert: Ensure correct behavior
        assertEquals(1, result.size());
        assertEquals(Support.SKI, result.get(0).getSupport());
        verify(courseRepository, times(1)).findAll();
    }

    @Test
    void testAddCourse() {
        // Arrange: Mock save()
        when(courseRepository.save(any(Course.class))).thenReturn(course);

        // Act: Add a course
        Course savedCourse = courseService.addCourse(course);

        // Assert: Ensure saved values
        assertEquals(TypeCourse.COLLECTIVE_ADULT, savedCourse.getTypeCourse());
        assertEquals(Support.SKI, savedCourse.getSupport());
        assertEquals(200.0f, savedCourse.getPrice());
        verify(courseRepository, times(1)).save(course);
    }

    @Test
    void testUpdateCourse() {
        // Arrange: Modify and mock save()
        course.setTypeCourse(TypeCourse.INDIVIDUAL);
        course.setSupport(Support.SNOWBOARD);
        when(courseRepository.save(any(Course.class))).thenReturn(course);

        // Act: Update course
        Course updatedCourse = courseService.updateCourse(course);

        // Assert: Ensure updated values
        assertEquals(TypeCourse.INDIVIDUAL, updatedCourse.getTypeCourse());
        assertEquals(Support.SNOWBOARD, updatedCourse.getSupport());
        verify(courseRepository, times(1)).save(course);
    }

    @Test
    void testRetrieveCourse() {
        // Arrange: Mock findById()
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));

        // Act: Retrieve the course
        Course result = courseService.retrieveCourse(1L);

        // Assert: Ensure correct course is retrieved
        assertEquals(1L, result.getNumCourse());
        assertEquals(Support.SKI, result.getSupport());
        verify(courseRepository, times(1)).findById(1L);
    }

    @Test
    void testRetrieveCourse_NotFound() {
        // Arrange: Simulate empty result
        when(courseRepository.findById(999L)).thenReturn(Optional.empty());

        // Act: Try to retrieve a non-existent course
        Course result = courseService.retrieveCourse(999L);

        // Assert: Should return null
        assertEquals(null, result);
        verify(courseRepository, times(1)).findById(999L);
    }
}
