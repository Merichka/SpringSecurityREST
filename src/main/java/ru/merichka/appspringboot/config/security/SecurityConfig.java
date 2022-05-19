package ru.merichka.appspringboot.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.merichka.appspringboot.config.handler.AccessDeniedHandler;
import ru.merichka.appspringboot.config.handler.AuthenticationFailureHandler;
import ru.merichka.appspringboot.config.handler.AuthenticationSuccessHandler;
import ru.merichka.appspringboot.config.handler.LoginSuccessHandler;
import ru.merichka.appspringboot.service.AppService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final AppService appService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationSuccessHandler authenticationSuccessHandler;
    private final AuthenticationFailureHandler authenticationFailureHandler;
    private final LoginSuccessHandler urlLogoutSuccessHandler;
    private final AccessDeniedHandler accessDeniedHandler;

    @Autowired
    public SecurityConfig(AppService appService,
                                     PasswordEncoder passwordEncoder,
                                     AuthenticationSuccessHandler authenticationSuccessHandler,
                                     AuthenticationFailureHandler authenticationFailureHandler,
                                     LoginSuccessHandler loginSuccessHandler,
                                     AccessDeniedHandler accessDeniedHandler) {
        this.appService = appService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.authenticationFailureHandler = authenticationFailureHandler;
        this.urlLogoutSuccessHandler = loginSuccessHandler;
        this.accessDeniedHandler = accessDeniedHandler;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(appService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/img/**", "/css/**", "/js/**", "/webjars/**").permitAll()
                .antMatchers("/api/users/*", "/api/roles").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler);
        http.formLogin()
                .loginPage("/")
                .permitAll()
                .loginProcessingUrl("/login")
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler);
        http.logout()
                .logoutUrl("/logout")
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/")
                .logoutSuccessHandler(urlLogoutSuccessHandler)
                .permitAll()
        ;
    }
}
