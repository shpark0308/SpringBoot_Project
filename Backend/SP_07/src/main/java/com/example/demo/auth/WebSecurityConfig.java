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
		/* ��� ù ȭ���� ��� ����ڳ� �� �� ������
		 * ȸ�������� �� ��� ��, ADMIN, USER �� ���� ȸ�����Կ� �����ϵ���
		 */
		http.authorizeRequests().antMatchers("/").permitAll().
		antMatchers("/css/**","/js/**","/assets/**").permitAll().antMatchers("/page1", "/page2").hasAnyRole("USER","ADMIN").
		anyRequest().authenticated();
		
		/* �α��ο� ���� : /page1���� �Ѿ || �α��ο� ���� : /�� �ٽ� �Ѿ */
		http.formLogin().loginPage("/page1").loginProcessingUrl("/j_spring_security_check").failureUrl("/").usernameParameter("j_username")
		.passwordParameter("j_password").permitAll();
		
		/* �α׾ƿ��� default ���� /logout���� �Ѿ */
		http.logout().logoutUrl("/logout").logoutSuccessUrl("/").permitAll();
		
		http.csrf().disable();
	}
	
	/* �ϴ� DB ���� ���⼭ �ϱ� */
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
		auth.inMemoryAuthentication().withUser("user").password(passwordEncoder().encode("1234")).roles("USER").and().withUser("admin").password(passwordEncoder().encode("1234")).roles("ADMIN");
	}
	
	@Bean // �ϴ� ��ȣȭ Bean ��ü ����
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
		
	}
	
	
}
