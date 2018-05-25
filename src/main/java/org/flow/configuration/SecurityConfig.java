/*
package org.flow.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity security) throws Exception {
        security.httpBasic().disable()
                .authorizeRequests().antMatchers("/users*").permitAll();

        security.authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll();

    }
}
// https://mvnrepository.com/artifact/org.springframework.security/spring-security-web
    compile group: 'org.springframework.security', name: 'spring-security-web', version: '5.0.5.RELEASE'
    // https://mvnrepository.com/artifact/org.springframework.security/spring-security-config
    compile group: 'org.springframework.security', name: 'spring-security-config', version: '5.0.5.RELEASE'
    // https://mvnrepository.com/artifact/org.springframework.security/spring-security-config
    compile group: 'org.springframework.security', name: 'spring-security-config', version: '5.0.5.RELEASE'
*/