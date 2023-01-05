package skhu.gdsc.tobelist.configure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();          // csrf 비활성화
        return http
                .authorizeRequests()                                   // Request에 인증,인가 부여 (보안검사 시작)
                .antMatchers("/login", "/signup").permitAll()
                .antMatchers( "/main/**").hasAnyRole("USER", "ADMIN")    // /main으로 시작하는 uri는 권한이 있어야 접속가능
                .anyRequest().authenticated()                              // 나머지 요청은 권한이 있어야 접속 가능
                .and()
                .formLogin()                                           // 폼 로그인 방식
                .loginProcessingUrl("/login")                          // 로그인 uri
                .defaultSuccessUrl("/main")                                // 로그인 성공시 리다이렉트되는 uri
                .and().build();
    }
}