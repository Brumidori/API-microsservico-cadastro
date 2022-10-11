package br.com.capgemini.start.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import br.com.capgemini.start.service.UsuarioService;

@Configuration
@EnableWebSecurity
public class SecurityConf extends WebSecurityConfigurerAdapter {

	@Autowired
	private UsuarioService usuarioService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//http.cors().and().authorizeRequests();
		
		http.authorizeRequests()
			.antMatchers("/grafico**").permitAll()
			
			.antMatchers(HttpMethod.POST, "/agendamento**").hasAnyRole("EDITAR")
			.antMatchers(HttpMethod.GET, "/agendamento/excluir**").hasAnyRole("EDITAR")
			
			.antMatchers(HttpMethod.POST, "/coach**").hasAnyRole("EDITAR")
			
			.antMatchers(HttpMethod.POST, "/gestor**").hasAnyRole("EDITAR")
			
			.antMatchers(HttpMethod.POST, "/start**").hasAnyRole("EDITAR")
			.antMatchers(HttpMethod.GET, "/start/billable**").hasAnyRole("BILLABLE")
			
			.antMatchers(HttpMethod.POST, "/turma**").hasAnyRole("EDITAR")
			
			.anyRequest().authenticated()
			.and()
				.formLogin().loginPage("/login").permitAll().defaultSuccessUrl("/")
			.and()
				.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).permitAll()
				.logoutSuccessUrl("/login");
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(usuarioService)
			.passwordEncoder(new BCryptPasswordEncoder());
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**", "/css/**", "/imagens/**", "/webjars/**", "/h2-console/**");
	}
}
