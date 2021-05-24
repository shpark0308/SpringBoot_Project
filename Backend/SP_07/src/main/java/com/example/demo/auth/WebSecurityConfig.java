package com.example.demo.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		/* 모든 첫 화면은 어느 사용자나 들어갈 수 있으나
		 * 회원가입을 한 사람 즉, ADMIN, USER 를 상대로 회원가입에 성공하도록
		 */
		http.authorizeRequests().antMatchers("/").permitAll().
		antMatchers("/css/**","/js/**","/assets/**").permitAll().antMatchers("/page1", "/page2").hasAnyRole("USER","ADMIN").
		anyRequest().authenticated();
		
		/* 로그인에 성공 : /page1으로 넘어감 || 로그인에 실패 : /로 다시 넘어감 */
		http.formLogin().loginPage("/page1").loginProcessingUrl("/j_spring_security_check").failureUrl("/").usernameParameter("j_username")
		.passwordParameter("j_password").permitAll();
		
		/* 로그아웃은 default 값인 /logout으로 넘어감 */
		http.logout().logoutUrl("/logout").logoutSuccessUrl("/").permitAll();
		
		http.csrf().disable();
	}
	
	/* 일단 DB 말고 여기서 하기 */
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
		auth.inMemoryAuthentication().withUser("user").password(passwordEncoder().encode("1234")).roles("USER").and().withUser("admin").password(passwordEncoder().encode("1234")).roles("ADMIN");
	}
	
	@Bean // 일단 암호화 Bean 객체 생성
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
		
	}
	
	
}
