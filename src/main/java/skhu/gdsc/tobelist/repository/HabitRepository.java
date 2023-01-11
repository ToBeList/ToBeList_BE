package skhu.gdsc.tobelist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import skhu.gdsc.tobelist.domain.Habit;
import skhu.gdsc.tobelist.domain.User;

import java.util.List;

public interface HabitRepository extends JpaRepository<Habit, Long> {
    List<Habit> findHabitsByUser(User user);

}
