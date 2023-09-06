package vn.spacepc.hischool.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import vn.spacepc.hischool.service.AccountService;

@Configuration
public class SecurityConfiguration {

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public DaoAuthenticationProvider authenticationProvider(AccountService accountService) {
    DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
    daoAuthenticationProvider.setUserDetailsService(accountService);
    daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
    return daoAuthenticationProvider;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .authorizeRequests(authorizeRequests ->
            authorizeRequests
                .requestMatchers("password/forgetPassword").permitAll() // Cho phép truy cập mà không cần đăng nhập
                .requestMatchers("/account/resetPassword", "/error/**").permitAll()
                .requestMatchers("/account/change-password-reset/**").permitAll()
                .requestMatchers("/transcript/manager/**").hasAnyRole("ADMIN", "MANAGER")
                .requestMatchers("/transcript/head-teacher/**").hasAnyRole("ADMIN", "MANAGER","HEADTEACHER")
                .requestMatchers("/student/manager/**").hasAnyRole("ADMIN", "MANAGER")
                .requestMatchers("/student/head-teacher/**").hasAnyRole("ADMIN", "MANAGER","HEADTEACHER")
                .requestMatchers("/parent/manager/**").hasAnyRole("ADMIN", "MANAGER")
                .requestMatchers("/parent/head-teacher/**").hasAnyRole("ADMIN", "MANAGER","HEADTEACHER")
                .requestMatchers("/score/list/").hasAnyRole("ADMIN", "MANAGER","TEACHER")
                .requestMatchers("/static/**").permitAll()
                .anyRequest().authenticated()

        )
        .formLogin(
            form -> form.loginPage("/showLoginPage")
                .loginProcessingUrl("/authenticateTheUser")
                .defaultSuccessUrl("/checkUserRole")
                .failureUrl("/showLoginPage?error=InvalidCredentials") // URL xử lý lỗi
                .permitAll()
        )
        .logout(
            logout -> logout.permitAll()
        )
        .exceptionHandling(
            configure -> configure
                .accessDeniedPage("/showPage403")
                .defaultAuthenticationEntryPointFor(
                    new HttpStatusEntryPoint(HttpStatus.BAD_REQUEST),
                    new AntPathRequestMatcher("/showPage400")
                )
        );

    return http.build();
  }

}
