package team.spy.domain.User.contoller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import team.spy.domain.User.annotation.SocialUser;
import team.spy.domain.User.dto.User;


@RestController
public class LoginController {

	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login()
	{
		return "login";
	}
	
	@RequestMapping(value = "/loginfailure", method = RequestMethod.GET)
	public String loginFailure()
	{
		//@SocialUser User user
		System.out.println("oauth failure!!");
		return "hello";
		
//		return "redirect:/board/list";
	}
	
	@RequestMapping(value = "/loginsucess", method = RequestMethod.GET)
	public String loginSuccess(@SocialUser User user)
	{
		System.out.println("oauth sucess!!");
		return "redirect:/boards/list";
	}
}
