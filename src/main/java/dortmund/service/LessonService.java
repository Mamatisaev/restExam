package dortmund.service;

import dortmund.dto.request.LessonRequest;
import dortmund.dto.response.LessonResponse;
import dortmund.dto.response.SimpleResponse;
import dortmund.dto.responseView.LessonResponseView;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LessonService {

    LessonResponse saveLesson(LessonRequest lessonRequest);

    List<LessonResponse> getAllLessons();
    LessonResponse getById(Long lessonId);

    SimpleResponse deleteLessonById(Long lessonId);

    LessonResponse updateLessonById(Long lessonId, LessonRequest lessonRequest);

    LessonResponseView getPaginationOfLessons(int page, int size);

}
