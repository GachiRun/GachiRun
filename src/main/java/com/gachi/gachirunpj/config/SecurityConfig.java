package com.gachi.gachirunpj.config;

import jakarta.servlet.DispatcherType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableGlobalAuthentication
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorize) -> {
                    authorize
                            .requestMatchers(new AntPathRequestMatcher("/user/**")).authenticated()
                            .requestMatchers(new AntPathRequestMatcher("/admin/**")).hasRole("admin");
                    authorize.anyRequest().permitAll();
                })
                /*.formLogin(login -> login
                        .loginPage("/")
                        .loginProcessingUrl("/login")
                        .failureUrl("/loginFail")
                        .defaultSuccessUrl("/loginSuccess")
                        .permitAll()
                )*/
                .formLogin((formLogin) -> {
                    /* 권한이 필요한 요청은 해당 url로 리다이렉트 */
                    formLogin
                            .loginPage("/login")
                            .loginProcessingUrl("/login")
                            .failureUrl("/loginFail")
                            .defaultSuccessUrl("/loginSuccess");
                })
                .logout(logout -> {
                    logout
                            .deleteCookies("remove")
                            .invalidateHttpSession(false)
                            .logoutUrl("/logout")
                            .logoutSuccessUrl("/logoutForm");
                });


        return http.build();
    }

}

