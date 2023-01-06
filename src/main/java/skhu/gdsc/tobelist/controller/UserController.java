package skhu.gdsc.tobelist.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import skhu.gdsc.tobelist.domain.DTO.LoginDTO;
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
}
