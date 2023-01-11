package skhu.gdsc.tobelist.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import skhu.gdsc.tobelist.domain.DTO.HabitDTO;
import skhu.gdsc.tobelist.domain.Daily;
import skhu.gdsc.tobelist.domain.Habit;
import skhu.gdsc.tobelist.domain.User;
import skhu.gdsc.tobelist.repository.DailyRepository;
import skhu.gdsc.tobelist.repository.HabitRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HabitService {
    private final HabitRepository habitRepository;

    private final DailyService dailyService;
    private final DailyRepository dailyRepository;

    // 읽기전용, User 정보로 해당 유저의 habit 리스트 반환
    @Transactional(readOnly = true)
    public List<Habit> findHabitsByUser(User user) {
        List<Habit> habits = habitRepository.findHabitsByUser(user);
        return habits;
    }

    // 받아온 정보로 Habit 객체 만들어서 DB에 저장
    @Transactional
    public ResponseEntity<String> save(HabitDTO habitDTO, User user) {
        Daily daily = dailyService.save(habitDTO);
        // 빌더
        Habit userHabit = Habit.builder()
                .goal(habitDTO.getGoal())
                .user(user)
                .daily(daily)
                .build();

        daily.getHabit().add(userHabit);
        dailyRepository.save(daily);
        habitRepository.saveAndFlush(userHabit);

        return ResponseEntity.ok("Habit 저장 성공");
    }


    //목표 체크 시 update
    @Transactional
    public HabitDTO updateByCheck(Long id, HabitDTO habitDTO){
        Habit habit = loadHabitById(id);
        habit.update(habitDTO);
        return habitRepository.saveAndFlush(habit).toDTO();
    }

    //habit 삭제 시
    @Transactional
    public void deleteById(Long id) {
        Habit habit = loadHabitById(id);

        // delete 메소드를 사용해야 값이 없을 경우의 예외처리 가능
        habitRepository.delete(habit);
    }

    //id에 목표가 존재하지 않을 때 사용됨.
    public Habit loadHabitById(Long id) {
        // orElseThrow 메소드는 null일 경우 예외 처리 -> 404 Not Found 상태코드와 메시지 전달
        return habitRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 목표입니다."));
    }
}
