package sqltutorial.evms_api.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class EvmsApiWebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private DataSource dataSource;	

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().passwordEncoder(new BCryptPasswordEncoder()).dataSource(dataSource)
		.usersByUsernameQuery("select username, password, enabled from users where username = ?")
        .authoritiesByUsernameQuery("select username, role from users where username = ?");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {		
		http.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
		.authorizeRequests()
		.antMatchers("/api/evms/vaccine_types").hasAnyRole("ADMIN", "USER")
		.antMatchers("/api/evms/vaccine_types/add", "/api/evms/vaccine_types/edit", "/api/evms/vaccine_types/delete**").hasRole("ADMIN")
		.and().httpBasic();
   }
	


}
