package ca.fraseric.examscheduler.api;

import org.springframework.boot.actuate.web.exchanges.HttpExchange;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@RestController
public class HelloWorldController {
  @GetMapping("/")
  public String root() {
   return "";
 }

  @RequestMapping(value = "/name", method = RequestMethod.GET)
  public String getName(Authentication authentication, Principal principal) {
    System.out.println(authentication.getName());
    System.out.println("-----------------");
    System.out.println(principal.getName());
    return "";
  }

  @RequestMapping(value = "/", method = RequestMethod.GET)
  public ModelAndView index(Principal user) {
    ModelAndView mav= new ModelAndView("/web/index");
    mav.addObject("user", user);
    return mav;
  }
}
