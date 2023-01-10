package skhu.gdsc.tobelist.service;

import lombok.Builder;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

import skhu.gdsc.tobelist.domain.DTO.TokenDTO;
import skhu.gdsc.tobelist.jwt.TokenProvider;
import skhu.gdsc.tobelist.repository.UserRepository;
import skhu.gdsc.tobelist.domain.User;
import skhu.gdsc.tobelist.domain.DTO.SignUpDTO;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Builder
public class UserService {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public TokenDTO login(String email, String password) {
        // 1. ID/PW 를 기반으로 Authentication 객체 생성
        // 이때 authentication 객체는 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);

        // 2. 실제 검증(사용자 비밀번호 체크)이 이루어지는 부분
        // authenticate 매서드가 실행될 때 JwtUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증된 정보를 기반으로 JWT 토큰 생성
        TokenDTO tokenDTO = tokenProvider.createToken(authentication);

        return tokenDTO;
    }

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
        roles.add("USER");

        User user = userRepository.saveAndFlush(User.builder()
                .email(signupDTO.getEmail())
                .nickname(signupDTO.getNickname())
                .password(passwordEncoder.encode(signupDTO.getPassword()))      // 암호화
                .roles(roles).build());

        return user.getId();
    }

    // Optional을 사용한다면 그 안에 들어있는 값은 Optional.get() 메서드를 통해 접근, 만약 빈 Optional 객체에 get() 메서드를 호출한 경우 NoSuchElementException이 발생
    public User findByEmail(String email){
        return userRepository.findByEmail(email).get();
    }

}
