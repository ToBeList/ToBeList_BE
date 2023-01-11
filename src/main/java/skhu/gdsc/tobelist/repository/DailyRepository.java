package skhu.gdsc.tobelist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import skhu.gdsc.tobelist.domain.Daily;

import java.time.LocalDate;

public interface DailyRepository extends JpaRepository<Daily, Long> {
    Daily findByDate(LocalDate localDate);
}
