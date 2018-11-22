package com.example.demo.config;


import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    //private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    AuthProvider authProvider;

    @Autowired
    ObjectMapper objectMapper;

	@Override
	public void configure(WebSecurity web) throws Exception{
		web.ignoring().antMatchers("/static/**");
	}
	
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        	//.csrf().disable()   
            .authorizeRequests()
                .antMatchers("/index").permitAll()
                .antMatchers("/not_auth/").permitAll()
                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/").permitAll()
                //.anyRequest().authenticated()
            .and()
	            .formLogin()
            	.loginPage("/login")
	            .loginProcessingUrl("/login") 
	            .usernameParameter("username")  
	            .passwordParameter("password")  
	            .successHandler(this::loginSuccessHandler)
	            .failureHandler(this::loginFailureHandler)
                .defaultSuccessUrl("/")
            
            	/*
            	.loginPage("/login")
                .loginProcessingUrl("/login")
                /*
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                            Authentication authentication) throws IOException, ServletException {
                        redirectStrategy.sendRedirect(request, response, "/mail/downloadlist");
                        }
                })
                */
                .permitAll()
            .and()
            .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                //.logoutSuccessUrl("/login")
                .logoutSuccessUrl("/") 
                .invalidateHttpSession(true)

            .and()
            	//.exceptionHandling().accessDeniedPage("/accessDenied")
        		.exceptionHandling().accessDeniedHandler(accessDeniedHandler())
                ;

        http.authenticationProvider(authProvider);
    }
    
    private void loginSuccessHandler(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        response.setStatus(HttpStatus.OK.value());
        objectMapper.writeValue(response.getWriter(), "success");
    }
 
    private void loginFailureHandler(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        objectMapper.writeValue(response.getWriter(), "fail");
    }
 
    /*
    private void logoutSuccessHandler(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        response.setStatus(HttpStatus.OK.value());
        objectMapper.writeValue(response.getWriter(), "Bye!");
    }
    */
    
    /*
    @Override
    protected void configure(AuthenticationManagerBuilder authManagerBuilder) throws Exception {
        //authManagerBuilder.inMemoryAuthentication().withUser(userProperties.getUsername()).password(userProperties.getPassword()).roles("USER");
    }
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}
	
	@Bean 
	@Override 
	public AuthenticationManager authenticationManagerBean() throws Exception { 
		return super.authenticationManagerBean(); 
	}
	*/

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public AccessDeniedHandler accessDeniedHandler(){
        return new CustomAccessDeniedHandler();
    }
}