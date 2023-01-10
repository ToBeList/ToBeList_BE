package skhu.gdsc.tobelist.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Habit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    private String goal;

    @JsonIgnore         // Response에 해당 필드를 제외하여 순환 참조 오류 제거
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
