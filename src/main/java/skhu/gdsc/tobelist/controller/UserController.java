package skhu.gdsc.tobelist.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import skhu.gdsc.tobelist.domain.DTO.LoginDTO;
import skhu.gdsc.tobelist.domain.DTO.SignUpDTO;
import skhu.gdsc.tobelist.domain.DTO.TokenDTO;
import skhu.gdsc.tobelist.service.UserService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public TokenDTO login(@RequestBody LoginDTO LoginRequestDto) {
        String email = LoginRequestDto.getEmail();
        String password = LoginRequestDto.getPassword();
        TokenDTO tokenDTO = userService.login(email, password);
        return tokenDTO;
    }

    @PostMapping("/signup")
    public String signUp(@RequestBody SignUpDTO signupDto) throws Exception {
        userService.signUp(signupDto);          // 회원가입 정보 저장

        return "redirect:/login";           // 로그인 페이지 리다이렉트
    }
}
