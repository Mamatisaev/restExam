package dortmund.serviceImpl;

import dortmund.dto.mapper.LessonMapper;
import dortmund.dto.request.LessonRequest;
import dortmund.dto.response.LessonResponse;
import dortmund.dto.response.SimpleResponse;
import dortmund.dto.responseView.LessonResponseView;
import dortmund.entity.Lesson;
import dortmund.exception.NotFoundException;
import dortmund.repository.LessonRepository;
import dortmund.service.LessonService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;

    private final LessonMapper lessonMapper;

    public LessonServiceImpl(LessonRepository lessonRepository, LessonMapper lessonMapper) {
        this.lessonRepository = lessonRepository;
        this.lessonMapper = lessonMapper;
    }

    @Override
    public LessonResponse saveLesson(LessonRequest lessonRequest) {
        Lesson lesson = lessonMapper.mapToEntity(lessonRequest);
        return lessonMapper.mapToResponse(lesson);
    }

    @Override
    public List<LessonResponse> getAllLessons() {
        List<LessonResponse> lessonResponses = new ArrayList<>();
        for (Lesson lesson : lessonRepository.findAll()) {
            lessonResponses.add(lessonMapper.mapToResponse(lesson));
        }
        return lessonResponses;
    }

    @Override
    public LessonResponse getById(Long lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(
                () -> new NotFoundException("Lesson with id " + lessonId + " is not found."));
        return lessonMapper.mapToResponse(lesson);
    }

    @Override
    public SimpleResponse deleteLessonById(Long lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(
                () -> new NotFoundException("Lesson with id " + lessonId + " is not found."));
        lessonRepository.delete(lesson);
        return new SimpleResponse("DELETED", "Lesson with id " + lessonId + " is successfully deleted.");
    }

    @Override
    public LessonResponse updateLessonById(Long lessonId, LessonRequest lessonRequest) {
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(
                () -> new NotFoundException("Lesson with id " + lessonId + " is not found."));
        Lesson lesson1 = lessonMapper.updateLesson(lesson, lessonRequest);
        return lessonMapper.mapToResponse(lesson1);
    }

    @Override
    public LessonResponseView getPaginationOfLessons(int page, int size) {
        LessonResponseView lessonResponseView = new LessonResponseView();
        Pageable pageable = PageRequest.of(page-1, size, Sort.by("lessonName"));
        List<LessonResponse> lessonResponses = new ArrayList<>();
        Page<Lesson> allLessons = lessonRepository.findAll(pageable);
        for (Lesson lesson : allLessons) {
            lessonResponses.add(lessonMapper.mapToResponse(lesson));
        }
        lessonResponseView.setLessonResponses(lessonResponses);
        lessonResponseView.setCurrentPage(pageable.getPageNumber() + 1);
        lessonResponseView.setTotalPages(allLessons.getTotalPages());
        return lessonResponseView;
    }

}