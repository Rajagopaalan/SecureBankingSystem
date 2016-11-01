package com.websystique.springmvc.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	@Qualifier("customUserDetailsService")
	UserDetailsService userDetailsService;

	@Autowired
	PersistentTokenRepository tokenRepository;

	@Autowired
	public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
		auth.authenticationProvider(authenticationProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/", "/list")
				.access("hasRole('USER') or hasRole('ADMIN') or hasRole('REGEMPLOYEE') or hasRole('MANAGER') or hasRole('MERCHANT')")
				.antMatchers("/newuser/**", "/delete-user-*").access("hasRole('ADMIN')")
				.antMatchers("/edit-user-*").access("hasRole('ADMIN') or hasRole('MANAGER')")
				.antMatchers("/pending_trans").access("hasRole('REGEMPLOYEE') or hasRole('MANAGER') or hasRole('ADMIN')")
				.antMatchers("/downloadLogs").access("hasRole('ADMIN')")
				.antMatchers("/approve-*").access("hasRole('REGEMPLOYEE') or hasRole('MANAGER')")
				.antMatchers("/delete-user-*").access("hasRole('ADMIN') or hasRole('REGEMPLOYEE') or hasRole('REGEMPLOYEE')")
				.antMatchers("/downloadPDF").access("hasRole('USER') or hasRole('MERCHANT')")
				.antMatchers("/managefunds").access("hasRole('USER') or hasRole('MERCHANT')")
				.antMatchers("/validateOtp").access("hasRole('USER') or hasRole('MERCHANT')")
				.antMatchers("/transferFund").access("hasRole('USER') or hasRole('MERCHANT')")
				.antMatchers("/transaction").access("hasRole('USER') or hasRole('MERCHANT')")
				.antMatchers("/creditcard").access("hasRole('USER') or hasRole('MERCHANT')")
				.antMatchers("/debitCC/**").access("hasRole('USER') or hasRole('MERCHANT')")
				.antMatchers("/creditCC/**").access("hasRole('USER') or hasRole('MERCHANT')")
				.antMatchers("makePayment").access("hasRole('USER' ) or hasRole('MERCHANT')")
				.antMatchers("cctransaction").access("hasRole('USER') or hasRole('MERCHANT')")
				.antMatchers("/interestGenerationCC/**").access("hasRole('USER') or hasRole('MERCHANT')")
				.antMatchers("/latePaymentCC/**").access("hasRole('USER') or hasRole('MERCHANT')")
				.antMatchers("/newCreditLimitCC/**").access("hasRole('USER') or hasRole('MERCHANT')")
				.antMatchers("/edit/**").access("hasRole('USER') or hasRole('MERCHANT')")
				.antMatchers("payCreditCard").access("hasRole('USER') or hasRole('MERCHANT')")
				.antMatchers("/transactions/latestTransactions").access("hasRole('USER') or hasRole('MERCHANT')")
				.antMatchers("/transactions/latestTransactions").access("hasRole('MANAGER') or hasRole('MERCHANT')")
				.antMatchers("submitpayment").access("hasRole('MERCHANT')")
				.and().formLogin().loginPage("/login")
				.loginProcessingUrl("/login").usernameParameter("ssoId").passwordParameter("password").and()
				.rememberMe().rememberMeParameter("remember-me").tokenRepository(tokenRepository)
				.tokenValiditySeconds(86400).and().csrf().and().exceptionHandling().accessDeniedPage("/Access_Denied");
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}

	@Bean
	public PersistentTokenBasedRememberMeServices getPersistentTokenBasedRememberMeServices() {
		PersistentTokenBasedRememberMeServices tokenBasedservice = new PersistentTokenBasedRememberMeServices(
				"remember-me", userDetailsService, tokenRepository);
		return tokenBasedservice;
	}

	@Bean
	public AuthenticationTrustResolver getAuthenticationTrustResolver() {
		return new AuthenticationTrustResolverImpl();
	}

}
