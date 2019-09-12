package team.spy.domain.User.resolve;

import static team.spy.enums.SocialType.FACEBOOK;
import static team.spy.enums.SocialType.GOOGLE;
import static team.spy.enums.SocialType.KAKAO;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import team.spy.domain.User.annotation.SocialUser;
import team.spy.domain.User.entity.User;
import team.spy.domain.User.repository.UserRepository;
import team.spy.enums.SocialType;

@Component
public class UserArgumentResolver implements HandlerMethodArgumentResolver{

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(SocialUser.class) != null && parameter.getParameterType().equals(User.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession();
        User user = (User) session.getAttribute("user");
        return getUser(user, session);
    }

    private User getUser(User user, HttpSession session) {
        if(user == null) {
            try {
                OAuth2AuthenticationToken authentication = (OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
                Map<String, Object> map = authentication.getPrincipal().getAttributes();

                map.entrySet().stream().map(i -> i.getKey() + " " + i.getValue()).collect(Collectors.joining(""));

                System.out.println("auth : " + authentication.getAuthorizedClientRegistrationId());
                
                User convertUser = convertUser(authentication.getAuthorizedClientRegistrationId(), map);
                
                if (user == null) {
                    user = userRepository.save(convertUser);
                }

                user = convertUser;

                setRoleIfNotSame(user, authentication, map);
                session.setAttribute("user", user);
            } catch (ClassCastException e) {
                return user;
            }
        }
        return user;
    }

    private User convertUser(String authority, Map<String, Object> map) {
    	
        if(FACEBOOK.equals(authority)) return getModernUser(FACEBOOK, map);
        else if(GOOGLE.equals(authority)) return getModernUser(GOOGLE, map);
        else if(KAKAO.equals(authority)) return getKaKaoUser(map);
        return null;
    }

    private User getModernUser(SocialType socialType, Map<String, Object> map) {

        return User.builder()
                .nickname(String.valueOf(map.get("name")))
                .email(String.valueOf(map.get("email")))
                .socialType(socialType)
                .createDate(LocalDateTime.now())
                .build();
    }

    private User getKaKaoUser(Map<String, Object> map) {

    	Map<String, String> propertyMap = (HashMap<String, String>) map.get("properties");
        long id = (long) map.get("id");

        return User.builder()
                .nickname(propertyMap.get("nickname"))
                .email(String.valueOf(map.get("kaccount_email")))
                .socialType(KAKAO)
                .createDate(LocalDateTime.now())
                .build();
    }

    private void setRoleIfNotSame(User user, OAuth2AuthenticationToken authentication, Map<String, Object> map) {
        if(!authentication.getAuthorities().contains(new SimpleGrantedAuthority(user.getSocialType().getRoleType()))) {
            SecurityContextHolder.getContext()
                                 .setAuthentication(
                                         new UsernamePasswordAuthenticationToken(
                                                 map,
                                                 "N/A",
                                                 AuthorityUtils.createAuthorityList(user.getSocialType().getRoleType()
                                                 )
                                         )
                                 );
        }
    }
}
