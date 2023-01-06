package skhu.gdsc.tobelist.service;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import skhu.gdsc.tobelist.domain.User;
import skhu.gdsc.tobelist.domain.DTO.SignUpDTO;
import skhu.gdsc.tobelist.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Builder
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public Long signUp(SignUpDTO signupDTO) throws Exception {
        if (userRepository.findByEmail(signupDTO.getEmail()).isPresent()){
            throw new Exception("이미 존재하는 이메일입니다.");
        }

        if (!signupDTO.getPassword().equals(signupDTO.getCheckedPassword())){
            throw new Exception("비밀번호가 일치하지 않습니다.");
        }

        // 기본으로 ROLE_USER 권한 부여
        List<String> roles = new ArrayList<>();
        roles.add("ROLE_USER");

        User user = userRepository.saveAndFlush(User.builder()
                .email(signupDTO.getEmail())
                .nickname(signupDTO.getNickname())
                .password(passwordEncoder.encode(signupDTO.getPassword()))      // 암호화
                .goal_cnt(0)
                .daily_cnt(0)
                .roles(roles).build());

        return user.getId();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // 해당 username이 없으면 404 에러코드와 메시지 전달
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 email 가입자가 없습니다."));
    }
}
