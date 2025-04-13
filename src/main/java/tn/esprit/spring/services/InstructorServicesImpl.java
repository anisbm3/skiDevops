package tn.esprit.spring.services;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.Instructor;
import tn.esprit.spring.repositories.ICourseRepository;
import tn.esprit.spring.repositories.IInstructorRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Service
public class InstructorServicesImpl implements IInstructorServices {

    private static final Logger logger = LogManager.getLogger(InstructorServicesImpl.class);

    private IInstructorRepository instructorRepository;
    private ICourseRepository courseRepository;

    @Override
    public Instructor addInstructor(Instructor instructor) {
        logger.info("Ajout d’un nouvel instructeur : {} {}", instructor.getFirstName(), instructor.getLastName());
        return instructorRepository.save(instructor);
    }

    @Override
    public List<Instructor> retrieveAllInstructors() {
        logger.info("Récupération de tous les instructeurs");
        List<Instructor> instructors = instructorRepository.findAll();
        logger.debug("Nombre d'instructeurs récupérés : {}", instructors.size());
        return instructors;
    }

    @Override
    public Instructor updateInstructor(Instructor instructor) {
        logger.info("Mise à jour de l’instructeur avec ID : {}", instructor.getNumInstructor());
        return instructorRepository.save(instructor);
    }

    @Override
    public Instructor retrieveInstructor(Long numInstructor) {
        logger.info("Recherche de l’instructeur avec ID : {}", numInstructor);
        Instructor instructor = instructorRepository.findById(numInstructor).orElse(null);
        if (instructor == null) {
            logger.warn("Aucun instructeur trouvé avec l’ID : {}", numInstructor);
        }
        return instructor;
    }

    @Override
    public Instructor addInstructorAndAssignToCourse(Instructor instructor, Long numCourse) {
        logger.info("Ajout d’un instructeur et assignation au cours ID : {}", numCourse);
        Course course = courseRepository.findById(numCourse).orElse(null);
        if (course == null) {
            logger.error("Le cours avec ID {} n’existe pas", numCourse);
            return null;
        }
        Set<Course> courseSet = new HashSet<>();
        courseSet.add(course);
        instructor.setCourses(courseSet);
        return instructorRepository.save(instructor);
    }
}
