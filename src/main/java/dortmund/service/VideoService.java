package dortmund.service;

import dortmund.dto.request.VideoRequest;
import dortmund.dto.response.VideoResponse;
import dortmund.dto.responseView.VideoResponseView;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface VideoService {

    VideoResponse saveVideo(VideoRequest videoRequest);

    List<VideoResponse> getAllVideos();

    VideoResponse getById(Long videoId);

    VideoResponse deleteVideo(Long videoId);

    VideoResponse updateVideoById(Long videoId, VideoRequest videoRequest);

    VideoResponseView getPaginationOfVideos(int page, int size);

}
