package dortmund.serviceImpl;

import dortmund.dto.mapper.VideoMapper;
import dortmund.dto.request.VideoRequest;
import dortmund.dto.response.VideoResponse;
import dortmund.dto.responseView.VideoResponseView;
import dortmund.entity.Video;
import dortmund.exception.NotFoundException;
import dortmund.repository.VideoRepository;
import dortmund.service.VideoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VideoServiceImpl implements VideoService {

    private final VideoMapper videoMapper;

    private final VideoRepository videoRepository;

    public VideoServiceImpl(VideoMapper videoMapper, VideoRepository videoRepository) {
        this.videoMapper = videoMapper;
        this.videoRepository = videoRepository;
    }

    @Override
    public VideoResponse saveVideo(VideoRequest videoRequest) {
        Video video = videoMapper.mapToEntity(videoRequest);
        return videoMapper.mapToResponse(video);
    }

    @Override
    public List<VideoResponse> getAllVideos() {
        List<VideoResponse> videoResponses = new ArrayList<>();
        for (Video video : videoRepository.findAll()) {
            videoResponses.add(videoMapper.mapToResponse(video));
        }
        return videoResponses;
    }

    @Override
    public VideoResponse getById(Long videoId) {
        Video video = videoRepository.findById(videoId).orElseThrow(
                () -> new NotFoundException("Video with id " + videoId + " is nor found."));
        return videoMapper.mapToResponse(video);
    }

    @Override
    public VideoResponse deleteVideo(Long videoId) {
        Video video = videoRepository.findById(videoId).orElseThrow(
                () -> new NotFoundException("Video with id " + videoId + " is not found."));
        video.setLesson(null);
        videoRepository.delete(video);
        return videoMapper.mapToResponse(video);
    }

    @Override
    public VideoResponse updateVideoById(Long videoId, VideoRequest videoRequest) {
        Video video = videoRepository.findById(videoId).orElseThrow(
                () -> new NotFoundException("Video with id " + videoId + " is not found."));
        Video video1 = videoMapper.updateVideo(video, videoRequest);
        return videoMapper.mapToResponse(video1);
    }

    @Override
    public VideoResponseView getPaginationOfVideos(int page, int size) {
        VideoResponseView videoResponseView = new VideoResponseView();
        Pageable pageable = PageRequest.of(page-1, size, Sort.by("videoName"));
        List<VideoResponse> videoResponses = new ArrayList<>();
        Page<Video> allVideos = videoRepository.findAll(pageable);
        for (Video video : allVideos) {
            videoResponses.add(videoMapper.mapToResponse(video));
        }
        videoResponseView.setVideoResponses(videoResponses);
        videoResponseView.setCurrentPage(pageable.getPageNumber() + 1);
        videoResponseView.setTotalPages(allVideos.getTotalPages());
        return videoResponseView;
    }

}