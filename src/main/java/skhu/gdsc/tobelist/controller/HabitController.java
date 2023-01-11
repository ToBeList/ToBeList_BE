package skhu.gdsc.tobelist.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import skhu.gdsc.tobelist.domain.DTO.HabitDTO;
import skhu.gdsc.tobelist.domain.Habit;
import skhu.gdsc.tobelist.domain.User;
import skhu.gdsc.tobelist.service.HabitService;
import skhu.gdsc.tobelist.service.UserService;

import java.util.List;

@RequestMapping("/main")
@RequiredArgsConstructor
@RestController
public class HabitController {
    private final HabitService habitService;
    private final UserService userService;

    @GetMapping("/habit")
    // Habit 전체 조회
    public ResponseEntity<List<Habit>> findAll(@AuthenticationPrincipal UserDetails userDetails) {

        /**
         *  HabitController, UserService, UserRepository, HabitService, HabitRepository 변경됨
         */
        // 1. UserDetails로 받아온 후 User 조회하기
        String email = userDetails.getUsername();
        User user = userService.findByEmail(email);

        // 2. UserRepository에서 findHabit... 으로 Habit을 조회해올 수 없다.
        // (JPQL 사용 시에는 가능할 것으로 추정되나, 메소드 이름으로 쿼리 자동 생성하려면 HabitRepository 에서 메소드를 만들어야 한다.
        // HabitRepository 에서 findHabitsBy... 로 가져와야 한다.
        List<Habit> responses = habitService.findHabitsByUser(user);

        // 값이 존재하지 않을 경우
        if (responses.isEmpty()) {
            return ResponseEntity
                    .ok(null);
        }

        return ResponseEntity.ok().body(responses);        // 200 OK - 요청 성공 , body에 responses 값을 담아보냄
    }


    @PostMapping("/habit")
    public ResponseEntity<String> save(@RequestBody HabitDTO requestDTO, @AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        User user = userService.findByEmail(email);

        ResponseEntity<String> response = habitService.save(requestDTO, user);       // 받아온 요청을 DB에 저장

        // ResponseEntity는 사용자의 HttpRequest에 대한 응답 데이터를 포함하는 클래스로 <Body, Headers, Status>를 포함한다.
        return response;
    }


    @DeleteMapping("/habit/{id}")
     public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
         habitService.deleteById(id);

         return ResponseEntity
                 .ok(null);
     }
}
