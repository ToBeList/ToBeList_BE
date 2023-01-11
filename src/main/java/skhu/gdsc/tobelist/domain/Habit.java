package skhu.gdsc.tobelist.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import skhu.gdsc.tobelist.domain.DTO.HabitDTO;

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

    @Column
    private String goal;

    @Column
    @ColumnDefault("false")
    private boolean checked;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "daily_id")
    private Daily daily;

    @JsonIgnore         // Response에 해당 필드를 제외하여 순환 참조 오류 제거
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public HabitDTO toDTO() {
        return HabitDTO.builder()
                .goal(goal)
                .checked(checked)
                .date(daily.getDate())
                .build();
    }

    public void update(HabitDTO habitDTO) {
        this.checked = habitDTO.isChecked();
    }
}