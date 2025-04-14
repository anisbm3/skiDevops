package tn.esprit.spring;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.LoggerContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.entities.Piste;
import tn.esprit.spring.repositories.IPisteRepository;
import tn.esprit.spring.services.PisteServicesImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PisteServicesImplTest {

    @Mock
    private IPisteRepository pisteRepository;

    @InjectMocks
    private PisteServicesImpl pisteServices;

    @Test
    void testRetrieveAllPistes() {
        // Arrange
        Piste p1 = new Piste();
        Piste p2 = new Piste();
        when(pisteRepository.findAll()).thenReturn(Arrays.asList(p1, p2));

        // Act
        List<Piste> result = pisteServices.retrieveAllPistes();

        // Assert
        assertEquals(2, result.size());
        verify(pisteRepository, times(1)).findAll();
    }

    @Test
    void testAddPiste() {
        // Arrange
        Piste newPiste = new Piste();
        when(pisteRepository.save(any(Piste.class))).thenReturn(newPiste);

        // Act
        Piste result = pisteServices.addPiste(newPiste);

        // Assert
        assertNotNull(result);
        verify(pisteRepository, times(1)).save(newPiste);
    }

    @Test
    void testRemovePiste() {
        // Arrange
        Long pisteId = 1L;
        doNothing().when(pisteRepository).deleteById(pisteId);

        // Act
        pisteServices.removePiste(pisteId);

        // Assert
        verify(pisteRepository, times(1)).deleteById(pisteId);
    }

    @Test
    void testRetrievePiste_WhenExists() {
        // Arrange
        Long pisteId = 1L;
        Piste expected = new Piste();
        when(pisteRepository.findById(pisteId)).thenReturn(Optional.of(expected));

        // Act
        Piste result = pisteServices.retrievePiste(pisteId);

        // Assert
        assertNotNull(result);
        assertEquals(expected, result);
    }

    @Test
    void testRetrievePiste_WhenNotExists() {
        // Arrange
        Long pisteId = 999L;
        when(pisteRepository.findById(pisteId)).thenReturn(Optional.empty());

        // Act
        Piste result = pisteServices.retrievePiste(pisteId);

        // Assert
        assertNull(result);
    }
}