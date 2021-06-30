package member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


import bean.MemberBean;
import dao.MemberDao;

// 2021-06-10_박현지
// 로그인 컨트롤러 

@Controller
public class MemberLoginController {
	private final String COMMAND = "/meLogin.me";
	
	@Autowired
	@Qualifier(value = "meDao")
	private MemberDao meDao;
	
	private MemberBean meBean;
	
	private ModelAndView mav;
	
	public MemberLoginController() {
		this.meBean = new MemberBean();
		this.mav =  new ModelAndView();
		this.meDao = new MemberDao();
	}
	
	@GetMapping(value = COMMAND)
	public String doGet() {
		return "meLogin";
	}
	
	@PostMapping(value = COMMAND)
	public ModelAndView doPost(
			@RequestParam(value = "meEmail") String meEmail,
			@RequestParam(value = "mePassword") String mePassword,
			HttpServletRequest request
			) {
		
		HttpSession session = request.getSession();
		
		this.meBean = this.meDao.selectByIdPw(meEmail,mePassword);
		
		if (meBean != null) { // 해당하는 아이디와 패스워드를 찾으면 
			System.out.println("로그인 성공");
			
			session.setAttribute("loginfo", meBean);
			
			mav.setViewName("redirect:/main.co");			
		}else { // 해당하는 아이디와 패스워드가 없으면 
			System.out.println("로그인 실패");
			session.setAttribute("err_message", "로그인 실패! 아이디나 패스워드가 틀렸습니다.");
			mav.setViewName("meLogin");
		}
		
		return mav;
	}
}
