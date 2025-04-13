package tn.esprit.spring;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.entities.Instructor;
import tn.esprit.spring.repositories.ICourseRepository;
import tn.esprit.spring.repositories.IInstructorRepository;
import tn.esprit.spring.services.InstructorServicesImpl;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class InstructorServicesImplTest {

    @Mock
    IInstructorRepository instructorRepository;

    @Mock
    ICourseRepository courseRepository;

    @InjectMocks
    InstructorServicesImpl instructorServices;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRetrieveAllInstructors() {
        Instructor i1 = new Instructor();
        i1.setFirstName("Fares");

        when(instructorRepository.findAll()).thenReturn(Arrays.asList(i1));

        List<Instructor> result = instructorServices.retrieveAllInstructors();

        assertEquals(1, result.size());
        assertEquals("Fares", result.get(0).getFirstName());
        verify(instructorRepository, times(1)).findAll();
    }
}
