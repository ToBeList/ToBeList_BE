package skhu.gdsc.tobelist.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skhu.gdsc.tobelist.domain.DTO.HabitDTO;
import skhu.gdsc.tobelist.domain.Daily;
import skhu.gdsc.tobelist.repository.DailyRepository;

import java.time.LocalDate;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class DailyService {

    private final DailyRepository dailyRepository;


    //날짜 찾아서 그만큼 daily 만들어줌.
    @Transactional
    public Daily save(HabitDTO habitDTO){
        Daily daily = dailyRepository.findByDate(habitDTO.getDate());
        if(daily == null) {
            daily = Daily.builder()
                    .date(habitDTO.getDate())
                    .habit(new ArrayList<>())
                    .build();
        } else {
            return daily;
        }

        return dailyRepository.save(daily);
    }

    //daily에서 날짜 찾아오기
    public Daily findByDate(LocalDate date) {
        return dailyRepository.findByDate(date);
    }
}
