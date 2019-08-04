package team.spy.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CharacterEncodingFilter;

import team.spy.domain.enums.SocialType;
import team.spy.oauth2.CustomOAuth2Provider;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.addAllowedOrigin(CorsConfiguration.ALL);
		configuration.addAllowedMethod(CorsConfiguration.ALL);
		configuration.addAllowedHeader(CorsConfiguration.ALL);
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		
		CharacterEncodingFilter filter = new CharacterEncodingFilter();
        
		http
				.authorizeRequests()
	            .antMatchers("/", "/oauth2/**", "/login/**",  "/css/**", "/images/**", "/js/**", "/console/**").permitAll()
	            .antMatchers("/facebook").hasAuthority(SocialType.FACEBOOK.getRoleType())
	            .antMatchers("/google").hasAuthority(SocialType.GOOGLE.getRoleType())
	//            .antMatchers("/kakao").hasAuthority(SocialType.KAKAO.getRoleType())
	            .anyRequest().authenticated()
            .and()
                .oauth2Login()
                .defaultSuccessUrl("/loginsucess")
                .failureUrl("/loginfailure")
            .and()
                .headers().frameOptions().disable()
            .and()
                .exceptionHandling()
                .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"))
//            .and()
//                .formLogin()
//                .successForwardUrl("/boards/list")
            .and()
                .logout()
                .logoutUrl("/home")
                .logoutSuccessUrl("/")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
            .and()
            	.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues())
            .and()
                .addFilterBefore(filter, CsrfFilter.class)
                .csrf().disable();
	}
	
	
	
	@Bean
	public ClientRegistrationRepository clientRegistrationRepository(OAuth2ClientProperties oAuth2ClientProperties, 
						@Value("${custom.oauth2.kakao.client-id}") String kakaoClientId) {
        
		List<ClientRegistration> registrations = oAuth2ClientProperties.getRegistration().keySet().stream()
                .map(client -> getRegistration(oAuth2ClientProperties, client))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        registrations.add(CustomOAuth2Provider.KAKAO.getBuilder("kakao")
                .clientId(kakaoClientId)
                .clientSecret("test") //필요없는 값인데 null이면 실행이 안되도록 설정되어 있음
                .jwkSetUri("test") //필요없는 값인데 null이면 실행이 안되도록 설정되어 있음
                .build());

        return new InMemoryClientRegistrationRepository(registrations);
    }
	
	private ClientRegistration getRegistration(OAuth2ClientProperties clientProperties, String client)
	{
		if("google".equals(client))
		{
			OAuth2ClientProperties.Registration registration = clientProperties.getRegistration().get("google");
			return CommonOAuth2Provider.GOOGLE.getBuilder(client).clientId(registration.getClientId())
																.clientSecret(registration.getClientSecret())
																.scope("email", "profile")
																.build();
		}
		
		if("facebook".equals(client))
		{
			OAuth2ClientProperties.Registration registration = clientProperties.getRegistration().get("facebook");
			return CommonOAuth2Provider.GOOGLE.getBuilder(client).clientId(registration.getClientId())
																.clientSecret(registration.getClientSecret())
																.scope("email")
																.userInfoUri("https://graph.facebook.com/me?"
																		+ "fields=id,name,emial,link")
																.build();
		}
		
		return null;
	}
	
}
