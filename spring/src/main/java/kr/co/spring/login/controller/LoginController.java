package kr.co.spring.login.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.spring.member.model.MemberVO;
import kr.co.spring.member.service.MemberService;

@Controller
public class LoginController {
   
   @Autowired
   MemberService memberService;
   
   @Autowired
   PasswordEncoder passwordEncoder;
   
   @RequestMapping(value = "login/loginForm")
   public String loginForm() {
      return "login/loginForm";
   }
   
   @RequestMapping(value = "login/login")
   public String login(
            @RequestParam(value="mem_id") String mem_id,
            @RequestParam(value="mem_pwd") String mem_pwd,
            Model model,
            HttpSession session
         ) throws Exception {
      
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("mem_id", mem_id);
      
    MemberVO member = memberService.getMember(paramMap);
    
    String message="";
    boolean isError = true;
    String viewPage="redirect:/";
    
    if(member != null) {
      boolean isPwdChk = passwordEncoder.matches(mem_pwd,  member.getMem_pwd());
      
      if(isPwdChk) {
         session.setAttribute("LOGIN_USER", member);
         message = member.getMem_name() + "님 환영합니다." ;
         String locationURL = "/member/memberList";
         model.addAttribute("locationURL",locationURL);
         
         isError = false;
      }else {
          isError = true;
          message = "회원정보가 없습니다. 아이디나 비밀번호를 확인해주세요";
         
      }
    }else {
       isError = true;
       message = "회원정보가 없습니다. 아이디나 비밀번호를 확인해주세요";
    }
    
    model.addAttribute("isError",isError);
    model.addAttribute("message",message);
    
    if(isError) {
       viewPage = "common/message";
    }
    //viewPage = "common/message";
    	return viewPage;
   }
   
   @RequestMapping(value = "login/logout")
   public String loginout(HttpSession session) {
	   session.invalidate();
		/* session.setAttribute("LOGIN_USER", null); */
		/* session.removeAttribute("LOGIN_USER"); */
	   return "redirect:/";
   }

  
}