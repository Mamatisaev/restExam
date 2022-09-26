package dortmund.repository;

import dortmund.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    @Override
    Page<Course> findAll(Pageable pageable);

    @Query("select c from  Course  c where  upper(c.courseName) like concat('%',:text,'%') ")
    List<Course> searchByCourseName(@Param("text") String text, Pageable pageable);

}