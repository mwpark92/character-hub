package team.spy.domain.User.contoller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorityTestController {

	@RequestMapping(value = "/facebook", method = RequestMethod.GET)
    public String facebook() {
        return "facebook";
    }

	@RequestMapping(value = "/google", method = RequestMethod.GET)
    public String google() {
        return "google";
    }

	@RequestMapping(value = "/kakao", method = RequestMethod.GET)
    public String kakao() {
        return "kakao";
    }
}