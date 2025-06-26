package com.canesblack.Spring_Project.config;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Configuration
@EnableWebSecurity
// security 기능을 사용하려면 해당 annotation을 써줘야 함.
public class SecurityConfig {
	
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		// spring security 기능을 사용하고자 할 때 해당 메소드 안에 작성.
		http
		.csrf(csrf->csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
		// csrf 해킹기법으로부터 보호하는 코드, 나중에 HTML과 자바스크립트에 csrf 보호 기능을 csrf토큰 넣어놓을 예정. 
		.cors(cors->cors.configurationSource(corsCorsConfigurationSource()))
		// cors는 특정 서버로부터만 데이터를 주고받을 수 있게 해주는 것.
		.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
		// 세션 설정
		.authorizeHttpRequests(authz->authz.requestMatchers("/","/loginPage","/logout","/noticeCheckPage","/registerPage","/menu/all")
				.permitAll()
				.requestMatchers(HttpMethod.POST,"/login","/register").permitAll()
				.requestMatchers("/resources/**","/WEB-INF/**").permitAll()
				.requestMatchers("/noticeAddPage","/noticeModifyPage").hasAnyAuthority("ADMIN","MANAGER")
				.requestMatchers("/menu/add").hasAnyAuthority("ADMIN","MANAGER")
				.requestMatchers("/menu/update").hasAnyAuthority("ADMIN","MANAGER")
				.requestMatchers("/menu/delete").hasAnyAuthority("ADMIN","MANAGER")
				.anyRequest().authenticated()
				)
		
		
		.formLogin(
				login->login.loginPage("/loginPage") // 로그인 페이지로 이동
				.loginProcessingUrl("/login")
				.failureUrl("/loginPage?error=true")
				.usernameParameter("username")
				.passwordParameter("password")
				.successHandler(authenticationSuccessHandler())
				.permitAll()				
				)
		
		.logout(logout->logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))// logout URL을 통해서 로그아웃 진행
				.logoutSuccessUrl("/") // 로그아웃 성공 후 해당 url로 리다이렉트
				.invalidateHttpSession(true) // 세션 무효화 -> 세션 공간에 있던 데이터 사라짐
				.deleteCookies("JSESSIONID") // 쿠키 삭제
				.permitAll()
				);

		
		return http.build();
		// 최종 http에 적용시킬때 사용하는 메소드
	}
	
	@Bean
	public AuthenticationSuccessHandler authenticationSuccessHandler() {
		return new SimpleUrlAuthenticationSuccessHandler() {
			
			@Override
			public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
					Authentication authentication) throws IOException, ServletException {
				// 로그인이 성공했을 때 , 우리가 특별 기능을 넣고 싶을 때 사용 (권한, 세션 기능 등)
				
				HttpSession session = request.getSession(); // 세션 기능을 가지고 옴.
				boolean isManager = authentication.getAuthorities().stream()
						.anyMatch(GrantedAuthority -> 
						GrantedAuthority.getAuthority().equals("ADMIN")||
						GrantedAuthority.getAuthority().equals("MANAGER"));
					if(isManager) {
						session.setAttribute("MANAGER",true);
					}
					session.setAttribute("username", authentication.getName());
					//session에다가 로그인한 아이디 저장.
					session.setAttribute("isAuthenticated", true);
					//seesioj에 로그인 여부 저장.
					// response.sendRedirect(request.getContextPath()) -> localhost:8080
					response.sendRedirect(request.getContextPath()+"/");
				super.onAuthenticationSuccess(request, response, authentication);
			}
			
		};
		
	}
	
	@Bean // passwor 암호화
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	@Bean
	public CorsConfigurationSource corsCorsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("http://localhost:8080","https://localhost:8080"));
		//localhost:8080 서버에서는 프론트 -> 백엔드 || 백엔드 -> 프론트로 데이터를 주고받을 수 있게 만든 것.
		configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE"));
		configuration.setAllowedHeaders(Arrays.asList("Authorization","Content-Type"));
		org.springframework.web.cors.UrlBasedCorsConfigurationSource source = new org.springframework.web.cors.UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}	
}
