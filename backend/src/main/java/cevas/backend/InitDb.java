package cevas.backend;

import cevas.backend.domain.Course;
import cevas.backend.domain.CourseReview;
import cevas.backend.domain.Member;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct // Indicate a method that should be executed after a bean has been initialized.
    public void init() {
        initService.dbInit1(); // 별도의 빈으로 등록해줘야 한다.
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;
        public void dbInit1() {
            Member m1 = Member.createMember("bob@gmail.com", "bob", "abc", "2022", "CS");
            Member m2 = Member.createMember("john@gmail.com", "john", "abc", "2018", "CS");
            Member m3 = Member.createMember("kate@gmail.com", "kate", "abc", "2021", "CS");
            Member m4 = Member.createMember("james@gmail.com", "james", "abc", "2020", "CS");
            Member m5 = Member.createMember("isaac@gmail.com", "isaac", "abc", "2019", "CS");
            Member m6 = Member.createMember("amanda@gmail.com", "amanda", "abc", "2023", "CS");

            em.persist(m1);
            em.persist(m2);
            em.persist(m3);
            em.persist(m4);
            em.persist(m5);
            em.persist(m6);

            Course c1 = Course.createCourse("COMP3230", "Operating System", "Eng");
            Course c2 = Course.createCourse("COMP3251", "Algorithm", "Eng");
            Course c3 = Course.createCourse("COMP3239", "Game", "Eng");
            Course c4 = Course.createCourse("COMP3270", "Database", "Eng");
            Course c5 = Course.createCourse("COMP3278", "Software Engineering", "Eng");
            Course c6 = Course.createCourse("COMP2501", "DataScience", "Eng");

            em.persist(c1);
            em.persist(c2);
            em.persist(c3);
            em.persist(c4);
            em.persist(c5);
            em.persist(c6);

            CourseReview cr1 = CourseReview.createCourseReview(
                    m1,
                    c1,
                    "2024",
                    "Dr. Bob",
                    "A+",
                    5,
                    5,
                    6,
                    5,
                    5,
                    5,
                    5,
                    20,
                    20,
                    20,
                    30
            );

            CourseReview cr2 = CourseReview.createCourseReview(
                    m2,
                    c1,
                    "2024",
                    "Dr. Bob",
                    "A+",
                    10,
                    10,
                    10,
                    10,
                    5,
                    5,
                    5,
                    20,
                    20,
                    20,
                    30
            );

            CourseReview cr3 = CourseReview.createCourseReview(
                    m3,
                    c1,
                    "2024",
                    "Dr. Bob",
                    "A+",
                    10,
                    10,
                    8,
                    5,
                    5,
                    5,
                    5,
                    20,
                    20,
                    20,
                    30
            );

            em.persist(cr1);
            em.persist(cr2);
            em.persist(cr3);
        }





    }
}