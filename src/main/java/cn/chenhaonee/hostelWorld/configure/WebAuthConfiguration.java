package cn.chenhaonee.hostelWorld.configure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Created by nichenhao on 2017/3/13.
 */
@Configuration
public class WebAuthConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    UserDetailsService myUserService(){
        return new MyUserService();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/index").permitAll()
                .antMatchers("/resources/css/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                    .loginPage("/login")
                    .permitAll()
                .and()
                .rememberMe()
                    .tokenValiditySeconds(1209600)
                    .key("myKey")
                .and()
                .logout()
                    .logoutUrl("/logout")
                    .permitAll();

    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
               .userDetailsService(myUserService())
                /*.passwordEncoder(new BCryptPasswordEncoder())*/;
    }
}
