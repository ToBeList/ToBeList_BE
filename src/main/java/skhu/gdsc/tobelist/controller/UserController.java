package skhu.gdsc.tobelist.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import skhu.gdsc.tobelist.domain.DTO.SignUpDTO;
import skhu.gdsc.tobelist.service.UserService;


@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public String signUp(@RequestBody SignUpDTO signupDto) throws Exception{
        userService.signUp(signupDto);

        return "redirect:/login";
    }
}
