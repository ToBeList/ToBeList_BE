package skhu.gdsc.tobelist.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import skhu.gdsc.tobelist.domain.DTO.HabitDTO;
import skhu.gdsc.tobelist.domain.Daily;
import skhu.gdsc.tobelist.domain.Habit;
import skhu.gdsc.tobelist.domain.User;
import skhu.gdsc.tobelist.service.DailyService;
import skhu.gdsc.tobelist.service.HabitService;
import skhu.gdsc.tobelist.service.UserService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/main")
public class MainController {

    private final UserService userService;
    private final HabitService habitService;

    private final DailyService dailyService;

    //main페이지에서 habit list 출력
    @GetMapping("")
    public ResponseEntity<List<Habit>> findAllByHabit (@AuthenticationPrincipal UserDetails userDetails) {

        String email = userDetails.getUsername();
        User user = userService.findByEmail(email);

        List<Habit> responses = habitService.findHabitsByUser(user);

        if (responses.isEmpty()) {
            return ResponseEntity
                    .ok(null);
        }

        return ResponseEntity.ok().body(responses);        // 200 OK - 요청 성공 , body에 responses 값을 담아보냄
    }

    //date 값 받아서 해당 날짜에 들어있는 habit 출력
    @GetMapping("/check/{date}")
    public Daily getDailyHabits(@PathVariable("date") String stringDate) {
        LocalDate date = LocalDate.parse(stringDate, DateTimeFormatter.ISO_DATE);
        return dailyService.findByDate(date);
    }

    //habit list에 들어있는 habit_id로 checked 값 변경
    @PatchMapping("/check/{id}")
    public ResponseEntity<HabitDTO> updateByCheck(@PathVariable("id") Long id, @RequestBody HabitDTO request) {
        HabitDTO response = habitService.updateByCheck(id,request);

        return ResponseEntity.ok(response);
    }
}
