package ru.merichka.appspringboot.config.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.merichka.appspringboot.config.handler.LoginSuccessHandler;


@Configuration
@AllArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsServiceImpl userDetailsService;
    private final LoginSuccessHandler loginSuccessHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage("/login")
                .successHandler(loginSuccessHandler)
                .loginProcessingUrl("/login")
                .usernameParameter("j_email")
                .passwordParameter("j_password")
                .permitAll();

        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/login").anonymous()
                .antMatchers("/").authenticated()
                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers("/user").hasRole("USER")
                .and()
                .formLogin()
                .successHandler(loginSuccessHandler);
    }

}
