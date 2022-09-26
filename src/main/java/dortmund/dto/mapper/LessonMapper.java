package dortmund.dto.mapper;

import dortmund.dto.request.LessonRequest;
import dortmund.dto.response.LessonResponse;
import dortmund.entity.Course;
import dortmund.entity.Lesson;
import dortmund.repository.CourseRepository;
import dortmund.repository.LessonRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LessonMapper {

    private final CourseRepository courseRepository;

    private final LessonRepository lessonRepository;

    public LessonMapper(CourseRepository courseRepository, LessonRepository lessonRepository) {
        this.courseRepository = courseRepository;
        this.lessonRepository = lessonRepository;
    }

    public Lesson mapToEntity(LessonRequest lessonRequest) {
        Course course = courseRepository.findById(lessonRequest.getCourseId()).get();
        Lesson lesson = new Lesson();
        lesson.setLessonName(lessonRequest.getLessonName());
        lesson.setCourses(course);
        course.setLessons(List.of(lesson));
        return lessonRepository.save(lesson);
    }

    public LessonResponse mapToResponse(Lesson lesson) {
        LessonResponse lessonResponse = new LessonResponse();
        lessonResponse.setLessonId(lesson.getId());
        lessonResponse.setLessonName(lesson.getLessonName());
        return lessonResponse;
    }

    public Lesson updateLesson(Lesson lesson, LessonRequest lessonRequest) {
        Course course = courseRepository.findById(lessonRequest.getCourseId()).get();
        lesson.setLessonName(lessonRequest.getLessonName());
        lesson.setCourses(course);
        return lessonRepository.save(lesson);
    }

}