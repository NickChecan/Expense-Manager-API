package com.expense.security;

import com.expense.setting.EnvironmentVariables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    private final EnvironmentVariables env;

    @Autowired
    public ResourceServerConfiguration(EnvironmentVariables env) {
        this.env = env;
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resourceConfig) {
        resourceConfig.resourceId(this.env.getResource());
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // Disable due to the android nature of the application
                .httpBasic().disable() // Disable default Spring Security default login process
                .logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .and().authorizeRequests()
                /* Access Related Routes */
                .antMatchers(HttpMethod.POST, "/signup").permitAll()
                .antMatchers(HttpMethod.GET, "/me").fullyAuthenticated()
                /* Roles */
                .antMatchers(HttpMethod.GET, "/role/**").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/role/**").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/role/**").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/role/**").hasAnyRole("ADMIN")
                /* Users */
                .antMatchers(HttpMethod.GET, "/user/**").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/user/**").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/user/**").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/user/**").hasAnyRole("ADMIN")
                /* Group */
                .antMatchers(HttpMethod.GET, "/group/**").hasAnyRole("USER")
                .antMatchers(HttpMethod.POST, "/group/**").hasAnyRole("USER")
                .antMatchers(HttpMethod.PUT, "/group/**").hasAnyRole("USER")
                .antMatchers(HttpMethod.DELETE, "/group/**").hasAnyRole("USER")
                /* Expense */
                .antMatchers(HttpMethod.GET, "/expense/**").hasAnyRole("USER")
                .antMatchers(HttpMethod.POST, "/expense/**").hasAnyRole("USER")
                .antMatchers(HttpMethod.PUT, "/expense/**").hasAnyRole("USER")
                .antMatchers(HttpMethod.DELETE, "/expense/**").hasAnyRole("USER")
                /* Any other requests... */
                .anyRequest().denyAll();
    }

}
