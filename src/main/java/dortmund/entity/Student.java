package dortmund.entity;

import dortmund.entity.enums.StudyFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "students")
@Getter
@Setter
@NoArgsConstructor
public class Student {
    @Id
    @GeneratedValue(generator = "student_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "student_generator", sequenceName = "student_id_sequence", allocationSize = 1)
    private Long id;
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "e_mail", unique = true)
    private String email;
    @Column(name = "study_format")
    @Enumerated(EnumType.STRING)
    private StudyFormat studyFormat;

    private boolean isActive = true;

    @CreatedDate
    private LocalDateTime created;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.REFRESH, CascadeType.DETACH}, fetch = FetchType.EAGER)
    private Course course;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Company company;

    public Student(String fullName, String phoneNumber, String email, StudyFormat studyFormat) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.studyFormat = studyFormat;
    }

}