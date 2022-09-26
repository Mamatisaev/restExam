package dortmund.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "lessons")
@Getter
@Setter
@NoArgsConstructor
public class Lesson {
    @Id
    @GeneratedValue(generator = "lesson_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "lesson_generator", sequenceName = "lesson_id_sequence", allocationSize = 1)
    private Long id;
    @Column(name = "lesson_name")
    private String lessonName;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "course_id")
    private Course courses;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lesson", fetch = FetchType.EAGER)
    private List<Task> tasks;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "lesson")
    private Video video;

    public void addTasks(Task task) {
        if (tasks == null) {
            tasks = new ArrayList<>();
        } else {
            this.tasks.add(task);
        }
    }

    public Lesson(String lessonName) {
        this.lessonName = lessonName;
    }
}