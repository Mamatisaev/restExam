package dortmund.controller;

import dortmund.dto.request.VideoRequest;
import dortmund.dto.response.VideoResponse;
import dortmund.dto.responseView.VideoResponseView;
import dortmund.serviceImpl.VideoServiceImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestControllerAdvice
@RequestMapping("api/videos")
@PreAuthorize("hasAuthority('ADMIN')")
public class VideoController {

    private final VideoServiceImpl videoServiceImpl;

    public VideoController(VideoServiceImpl videoServiceImpl) {
        this.videoServiceImpl = videoServiceImpl;
    }

    @PostMapping("/saveVideo")
    @PreAuthorize("hasAuthority('INSTRUCTOR')")
    public VideoResponse saveVideo(@RequestBody VideoRequest videoRequest) {
        return videoServiceImpl.saveVideo(videoRequest);
    }

    @GetMapping("/getAllVideos")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'INSTRUCTOR')")
    public List<VideoResponse> getVideos() {
        return videoServiceImpl.getAllVideos();
    }

    @GetMapping("/getVideo/{videoId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'INSTRUCTOR')")
    public VideoResponse getById(@PathVariable Long videoId) {
        return videoServiceImpl.getById(videoId);
    }

    @PreAuthorize("hasAuthority('INSTRUCTOR')")
    @DeleteMapping("/deleteVideo/{videoId}")
    public VideoResponse deleteVideo(@PathVariable Long videoId) {
        return videoServiceImpl.deleteVideo(videoId);
    }


    @PutMapping("/updateVideo/{videoId}")
    @PreAuthorize("hasAuthority('INSTRUCTOR')")
    public VideoResponse updateVideoById(@PathVariable Long videoId,
                                         @RequestBody VideoRequest videoRequest) {
        return videoServiceImpl.updateVideoById(videoId, videoRequest);
    }

    @GetMapping("/paginationOfVideos")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'INSTRUCTOR')")
    public VideoResponseView getPaginationOfVideos(@RequestParam int page,
                                                   @RequestParam int size) {
        return videoServiceImpl.getPaginationOfVideos(page, size);
    }

}