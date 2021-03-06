package com.example.springcompanyemployeeexample.config;

import com.example.springcompanyemployeeexample.entity.UserRole;
import com.example.springcompanyemployeeexample.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsImpl userDetails;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .formLogin()
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .and().authorizeRequests()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
//                .antMatchers(HttpMethod.GET, "/").permitAll()
//                .antMatchers(HttpMethod.GET, "/users").permitAll()
//                .antMatchers(HttpMethod.GET, "/user/activate").permitAll()
//                .antMatchers(HttpMethod.GET, "/addUser").permitAll()
//                .antMatchers(HttpMethod.POST, "/addUser").permitAll()
//                .antMatchers("/employees").authenticated()
//                .antMatchers("/employees").hasAnyAuthority(UserRole.ADMIN.name())
                .antMatchers("/employees/add").hasAnyAuthority(UserRole.USER.name())
                .antMatchers("/deleteUser/{id}").hasAnyAuthority(UserRole.ADMIN.name())
                .antMatchers("/deleteCompany/{id}").hasAnyAuthority(UserRole.ADMIN.name())
                .anyRequest().permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetails)
                .passwordEncoder(passwordEncoder());
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
