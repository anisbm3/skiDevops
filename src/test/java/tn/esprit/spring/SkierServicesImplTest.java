package tn.esprit.spring;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.entities.*;
import tn.esprit.spring.repositories.*;
import tn.esprit.spring.services.SkierServicesImpl;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

public class SkierServicesImplTest {

    @InjectMocks
    private SkierServicesImpl skierServices;

    @Mock
    private ISkierRepository skierRepository;

    @Mock
    private ISubscriptionRepository subscriptionRepository;

    @Mock
    private ICourseRepository courseRepository;

    @Mock
    private IRegistrationRepository registrationRepository;

    @Mock
    private IPisteRepository pisteRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddSkier() {
        // Données de test
        Skier skier = new Skier();
        Subscription subscription = new Subscription();
        subscription.setStartDate(LocalDate.now());
        subscription.setTypeSub(TypeSubscription.ANNUAL);
        skier.setSubscription(subscription);

        // Comportement simulé
        when(skierRepository.save(any(Skier.class))).thenReturn(skier);

        // Appel de la méthode
        Skier savedSkier = skierServices.addSkier(skier);

        // Assertions
        assertNotNull(savedSkier);
        assertEquals(LocalDate.now().plusYears(1), savedSkier.getSubscription().getEndDate());
        verify(skierRepository).save(skier);
    }

    @Test
    public void testRetrieveAllSkiers() {
        List<Skier> skiers = Collections.singletonList(new Skier());
        when(skierRepository.findAll()).thenReturn(skiers);

        List<Skier> result = skierServices.retrieveAllSkiers();

        assertEquals(1, result.size());
        verify(skierRepository).findAll();
    }

    @Test
    public void testAssignSkierToSubscription() {
        Skier skier = new Skier();
        skier.setNumSkier(1L); // Assurez-vous que cela correspond à votre méthode
        Subscription subscription = new Subscription();
        subscription.setNumSub(1L); // Utilisez 'setNumSub' au lieu de 'setId'

        when(skierRepository.findById(1L)).thenReturn(Optional.of(skier));
        when(subscriptionRepository.findById(1L)).thenReturn(Optional.of(subscription));
        when(skierRepository.save(skier)).thenReturn(skier);

        Skier updatedSkier = skierServices.assignSkierToSubscription(1L, 1L);

        assertNotNull(updatedSkier);
        assertEquals(subscription, updatedSkier.getSubscription());
        verify(skierRepository).save(skier);
    }

    @Test
    public void testAddSkierAndAssignToCourse() {
        Skier skier = new Skier();
        Course course = new Course();
        course.setNumCourse(1L); // Correction ici
        skier.setRegistrations(new HashSet<>(Collections.singletonList(new Registration())));

        when(skierRepository.save(skier)).thenReturn(skier);
        when(courseRepository.getById(1L)).thenReturn(course);
        when(registrationRepository.save(any(Registration.class))).thenReturn(new Registration());

        Skier result = skierServices.addSkierAndAssignToCourse(skier, 1L);

        assertNotNull(result);
        verify(skierRepository).save(skier);
        verify(courseRepository).getById(1L);
        verify(registrationRepository, times(1)).save(any(Registration.class));
    }
    @Test
    public void testRemoveSkier() {
        Long skierId = 1L;
        skierServices.removeSkier(skierId);
        verify(skierRepository).deleteById(skierId);
    }

    @Test
    public void testRetrieveSkier() {
        Skier skier = new Skier();
        skier.setNumSkier(1L); // Correction de setId() à setNumSkier()

        when(skierRepository.findById(1L)).thenReturn(Optional.of(skier));

        Skier result = skierServices.retrieveSkier(1L);

        assertNotNull(result);
        assertEquals(skier, result);
    }

    @Test
    public void testAssignSkierToPiste() {
        Skier skier = new Skier();
        Piste piste = new Piste();
        piste.setNumPiste(1L); // Correction ici
        skier.setNumSkier(1L); // Correction ici
        skier.setPistes(new HashSet<>());

        when(skierRepository.findById(1L)).thenReturn(Optional.of(skier));
        when(pisteRepository.findById(1L)).thenReturn(Optional.of(piste));
        when(skierRepository.save(skier)).thenReturn(skier);

        Skier updatedSkier = skierServices.assignSkierToPiste(1L, 1L);

        assertNotNull(updatedSkier);
        assertTrue(updatedSkier.getPistes().contains(piste));
        verify(skierRepository).save(skier);
    }


    @Test
    public void testRetrieveSkiersBySubscriptionType() {
        TypeSubscription typeSubscription = TypeSubscription.ANNUAL;
        List<Skier> skiers = Collections.singletonList(new Skier());

        when(skierRepository.findBySubscription_TypeSub(typeSubscription)).thenReturn(skiers);

        List<Skier> result = skierServices.retrieveSkiersBySubscriptionType(typeSubscription);

        assertEquals(1, result.size());
        verify(skierRepository).findBySubscription_TypeSub(typeSubscription);
    }
}