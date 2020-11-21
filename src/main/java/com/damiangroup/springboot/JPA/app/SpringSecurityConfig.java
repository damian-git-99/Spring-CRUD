package com.damiangroup.springboot.JPA.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.damiangroup.springboot.JPA.app.auth.handler.LoginSuccessHandler;

@EnableGlobalMethodSecurity(securedEnabled = true) // habilitar anotaciones 
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private LoginSuccessHandler loginSuccessHandler;
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder builder) throws Exception {
		
		//Como de va a codificar las password
		PasswordEncoder encoder = this.passwordEncoder();
		UserBuilder users = User.builder().passwordEncoder(password -> {
			return encoder.encode(password);
		});
		
		// Creamos un usuario en memoria
		builder.inMemoryAuthentication()
			.withUser(users.username("admin").password("1234").roles("ADMIN","USER"))
			.withUser(users.username("andres").password("1234").roles("USER"));
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		//Configuramos los distintos permisos
		http.authorizeRequests().antMatchers("/","/css/**","/js/**","/listar").permitAll()
			.antMatchers("/ver/**").hasAnyRole("USER")
			.antMatchers("/uploads/**").hasAnyRole("USER")
			//.antMatchers("/form/**").hasAnyRole("ADMIN")
			//.antMatchers("/eliminar/**").hasAnyRole("ADMIN")
			//.antMatchers("/factura/**").hasAnyRole("ADMIN")
			.anyRequest().authenticated()
			.and()
				.formLogin().successHandler(loginSuccessHandler)
				.loginPage("/login").permitAll()
			.and()
			.logout().permitAll()
			.and()
			.exceptionHandling().accessDeniedPage("/error_403");
			
	}
	
	

}
