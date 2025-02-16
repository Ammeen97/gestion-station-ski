package tn.esprit.spring.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.Instructor;
import tn.esprit.spring.repositories.ICourseRepository;
import tn.esprit.spring.repositories.IInstructorRepository;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InstructorServicesImplTest {

    @Mock
    private IInstructorRepository instructorRepository;

    @Mock
    private ICourseRepository courseRepository;

    @InjectMocks
    private InstructorServicesImpl instructorService;

    private Instructor instructor;
    private Course course;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Sample Instructor object
        instructor = new Instructor();
        instructor.setNumInstructor(1L);
        instructor.setFirstName("John");
        instructor.setLastName("Doe");
        instructor.setDateOfHire(LocalDate.of(2020, 1, 1));
        instructor.setCourses(new HashSet<>());

        // Sample Course object
        course = new Course();
        course.setNumCourse(101L);
        course.setLevel(3);
    }

    @Test
    void testAddInstructor() {
        // Arrange
        when(instructorRepository.save(any(Instructor.class))).thenReturn(instructor);

        // Act
        Instructor savedInstructor = instructorService.addInstructor(instructor);

        // Assert
        assertNotNull(savedInstructor);
        assertEquals("John", savedInstructor.getFirstName());
        verify(instructorRepository, times(1)).save(instructor);
    }

    @Test
    void testRetrieveAllInstructors() {
        // Arrange
        List<Instructor> instructorList = Arrays.asList(instructor);
        when(instructorRepository.findAll()).thenReturn(instructorList);

        // Act
        List<Instructor> result = instructorService.retrieveAllInstructors();

        // Assert
        assertEquals(1, result.size());
        verify(instructorRepository, times(1)).findAll();
    }

    @Test
    void testUpdateInstructor() {
        // Arrange
        instructor.setLastName("Smith");
        when(instructorRepository.save(any(Instructor.class))).thenReturn(instructor);

        // Act
        Instructor updatedInstructor = instructorService.updateInstructor(instructor);

        // Assert
        assertEquals("Smith", updatedInstructor.getLastName());
        verify(instructorRepository, times(1)).save(instructor);
    }

    @Test
    void testRetrieveInstructor() {
        // Arrange
        when(instructorRepository.findById(1L)).thenReturn(Optional.of(instructor));

        // Act
        Instructor result = instructorService.retrieveInstructor(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getNumInstructor());
        verify(instructorRepository, times(1)).findById(1L);
    }

    @Test
    void testRetrieveInstructor_NotFound() {
        // Arrange
        when(instructorRepository.findById(99L)).thenReturn(Optional.empty());

        // Act
        Instructor result = instructorService.retrieveInstructor(99L);

        // Assert
        assertNull(result);
        verify(instructorRepository, times(1)).findById(99L);
    }

    @Test
    void testAddInstructorAndAssignToCourse() {
        // Arrange
        when(courseRepository.findById(101L)).thenReturn(Optional.of(course));
        when(instructorRepository.save(any(Instructor.class))).thenReturn(instructor);

        // Act
        Instructor result = instructorService.addInstructorAndAssignToCourse(instructor, 101L);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getCourses().size());
        assertTrue(result.getCourses().contains(course));

        verify(courseRepository, times(1)).findById(101L);
        verify(instructorRepository, times(1)).save(instructor);
    }

    @Test
    void testAddInstructorAndAssignToCourse_CourseNotFound() {
        // Arrange: Simulate that the course is not found
        when(courseRepository.findById(999L)).thenReturn(Optional.empty());
        when(instructorRepository.save(any(Instructor.class))).thenReturn(instructor);

        // Act: Attempt to assign a non-existent course
        Instructor result = instructorService.addInstructorAndAssignToCourse(instructor, 999L);

        // Assert: Instructor is saved but should not have any new courses
        assertNotNull(result);
        assertTrue(result.getCourses() == null || result.getCourses().isEmpty());

        // Verify method calls
        verify(courseRepository, times(1)).findById(999L);
        verify(instructorRepository, times(1)).save(instructor);
    }

}
