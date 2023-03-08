package com.ecard.vCard.Confiq;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
public class SecurityConfiq extends WebSecurityConfigurerAdapter {

    @Autowired
    private HttpSession httpSession;

    @Bean
    public AuthenticationProvider activeDirectoryLdapAuthenticationProvider() {
    ActiveDirectoryLdapAuthenticationProvider activeDirectoryLdapAuthenticationProvider = 
        new ActiveDirectoryLdapAuthenticationProvider("berita1.tv", "ldap://192.168.10.10");
    activeDirectoryLdapAuthenticationProvider.setConvertSubErrorCodesToExceptions(true); 
    return activeDirectoryLdapAuthenticationProvider;
    }
    
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(activeDirectoryLdapAuthenticationProvider());   
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/assets/**")
                .antMatchers("/css/**")
                .antMatchers("/image/**")
                .antMatchers("/images/**")
                .antMatchers("/img/**")
                .antMatchers("/js/**")
                .antMatchers("/json/**");
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // http.csrf().and().headers().xssProtection().and().frameOptions().sameOrigin().and()
        http.csrf().disable()
                .headers().frameOptions().sameOrigin().and()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                // .antMatchers("/person/**", "/role/**", "/Work/**").hasAuthority("Administrator")
                // .antMatchers("/person/**", "/role/**").hasAuthority("Administrator")
                // .antMatchers("/video/**").hasAnyAuthority("BeritaSatu", "InvestorDaily", "InvestorID", "JakGlobe","MajalahInvestor, Ingest")
                // .antMatchers("/video/**").hasAnyAuthority("Produser", "Editor", "Uploader", "Library")
                // .antMatchers("/kontri/**").hasAnyAuthority("Kontri")
                .antMatchers("/").authenticated()
                .and()
                .formLogin()
                .failureUrl("/login?error")
                // .loginPage("/login")
                .and()
                .logout().deleteCookies("JSESSIONID")
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login")
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .and()
                .sessionManagement()
                .sessionFixation().migrateSession()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .maximumSessions(1)
                .expiredUrl("/login?expired")
                .and()
                .invalidSessionUrl("/login");
    }
}
