package dortmund.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "videos")
@Getter
@Setter
@NoArgsConstructor
public class Video {
    @Id
    @GeneratedValue(generator = "video_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "video_generator", sequenceName = "video_id_sequence", allocationSize = 1)
    private Long id;
    @Column(name = "video_name")
    private String videoName;
    @Column(unique = true)
    private String link;

    private boolean isActive = true;

    @CreatedDate
    private LocalDateTime created;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.REFRESH, CascadeType.DETACH})
    private Lesson lesson;

    public Video(String videoName, String link) {
        this.videoName = videoName;
        this.link = link;
    }
}