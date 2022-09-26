package dortmund.dto.mapper;

import dortmund.dto.request.VideoRequest;
import dortmund.dto.response.VideoResponse;
import dortmund.entity.Lesson;
import dortmund.entity.Video;
import dortmund.repository.LessonRepository;
import dortmund.repository.VideoRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class VideoMapper {

    private final LessonRepository lessonRepository;

    private final VideoRepository videoRepository;

    public VideoMapper(LessonRepository lessonRepository, VideoRepository videoRepository) {
        this.lessonRepository = lessonRepository;
        this.videoRepository = videoRepository;
    }

    public Video mapToEntity(VideoRequest videoRequest) {
        Lesson lesson = lessonRepository.findById(videoRequest.getLessonId()).get();
        Video video = new Video();
        video.setVideoName(videoRequest.getVideoName());
        video.setLink(videoRequest.getLink());
        video.setCreated(LocalDateTime.now());
        video.setActive(true);
        video.setLesson(lesson);
        lesson.setVideo(video);
        return videoRepository.save(video);

    }

    public VideoResponse mapToResponse(Video video) {
        VideoResponse videoResponse = new VideoResponse();
        videoResponse.setVideoId(video.getId());
        videoResponse.setVideoName(video.getVideoName());
        videoResponse.setLink(video.getLink());
        videoResponse.setCreated(LocalDateTime.now());
        videoResponse.setActive(video.isActive());
        return videoResponse;
    }

    public Video updateVideo(Video video, VideoRequest videoRequest) {
        Lesson lesson = lessonRepository.findById(videoRequest.getLessonId()).get();
        video.setVideoName(videoRequest.getVideoName());
        video.setLink(videoRequest.getLink());
        video.setCreated(LocalDateTime.now());
        video.setActive(true);
        video.setLesson(lesson);
        return videoRepository.save(video);
    }

}