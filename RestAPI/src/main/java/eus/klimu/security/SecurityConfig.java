package eus.klimu.security;

import eus.klimu.security.filter.AuthenticationFilter;
import eus.klimu.security.filter.AuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Manage the configuration of the security on the application.
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * Take care of fetching the users from the database and generates a Spring Security user with
     * SimpleGrantedAuthorities based on a user on the database, and it's roles. The implementation
     * of this class can be located at <b><u>UserServiceImp</u></b>.
     */
    private final UserDetailsService userDetailsService;

    /**
     * Password Encoder for the users, it uses a hashing system to transform the password into a hash
     * to avoid saving the password as plain text. BCrypt uses a random salt by default, and it is stored
     * together with the hash. This allows us to encode the password with both a hash and salt.
     */
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    /**
     * Configure the userDetailsService and the password encoder.
     * @param auth The authentication manager builder.
     * @throws Exception Generated if any operation failed.
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    /**
     * Configure the http security of the application.
     * @param http The http security of the application.
     * @throws Exception Generated if any operation failed.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(authenticationManagerBean());
        authenticationFilter.setFilterProcessesUrl("/login");

        // Disable default configuration.
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // ACL list.
        http.authorizeRequests()
                .antMatchers(
                        "/login/**", "/access/**", "/role/name/**", "/github-webhook/**",
                        "/channel/all", "/user-notification/create", "/user/create").permitAll()
                .anyRequest().authenticated()
            .and()
                .formLogin().loginProcessingUrl("/login").permitAll()
            .and()
                .exceptionHandling().accessDeniedPage("/access/denied");

        // Add filters.
        http.addFilter(authenticationFilter);
        http.addFilterBefore(new AuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * The authentication manager bean.
     * @return An authentication manager.
     * @throws Exception Generated if any operation failed.
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
