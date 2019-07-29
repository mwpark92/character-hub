package team.spy.domain.User.resolve;

import static team.spy.domain.enums.SocialType.FACEBOOK;
import static team.spy.domain.enums.SocialType.GOOGLE;
import static team.spy.domain.enums.SocialType.KAKAO;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

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
import team.spy.domain.User.dto.User;
import team.spy.domain.User.repository.UserRepository;
import team.spy.domain.enums.SocialType;

@Component
public class UserArgumentResolver implements HandlerMethodArgumentResolver{

    private UserRepository userRepository;

    public UserArgumentResolver(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

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
                
                for(Map.Entry<String, Object> entry : map.entrySet())
                {
                	System.out.println(entry.getKey() + "  " + entry.getValue());
                }
                
                System.out.println("auth : " + authentication.getAuthorizedClientRegistrationId());
                
                User convertUser = convertUser(authentication.getAuthorizedClientRegistrationId(), map);
                
                System.out.println(convertUser.toString());
                
                user = userRepository.findByPincipal(convertUser.getPincipal());
                
                if (user == null) { user = userRepository.save(convertUser); }

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
                .pincipal(String.valueOf(map.get("id")))
                .socialType(socialType)
                .createDate(LocalDateTime.now())
                .build();
    }

    private User getKaKaoUser(Map<String, Object> map) {
        
    	System.out.println("In");
    	
    	Map<String, String> propertyMap = (HashMap<String, String>) map.get("properties");
        String id = (String) map.get("id");
    	System.out.println("id : " + id);
        return User.builder()
                .nickname(propertyMap.get("nickname"))
                .email(String.valueOf(map.get("kaccount_email")))
                .pincipal(id)
                .socialType(KAKAO)
                .createDate(LocalDateTime.now())
                .build();
    }

    private void setRoleIfNotSame(User user, OAuth2AuthenticationToken authentication, Map<String, Object> map) {
        if(!authentication.getAuthorities().contains(new SimpleGrantedAuthority(user.getSocialType().getRoleType()))) {
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(map, "N/A", AuthorityUtils.createAuthorityList(user.getSocialType().getRoleType())));
        }
    }
}
