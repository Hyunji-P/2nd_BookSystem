package member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import bean.MemberBean;
import dao.MemberDao;

// 2021-06-10_박현지
// 회원 로그아웃 컨트롤러 

@Controller
public class MemberLogoutController {
	private final String COMMAND = "/meLogout.me";
	
	@Autowired
	@Qualifier(value = "meDao")
	private MemberDao meDao;
	
	private MemberBean meBean;
	
	private ModelAndView mav;
	
	public MemberLogoutController() {
		this.meBean = new MemberBean();
		this.mav =  new ModelAndView();
		this.meDao = new MemberDao();
	}
	
	@GetMapping(value = COMMAND)
	public String doGet(
			HttpServletRequest request) {
		HttpSession session = request.getSession();
		
		this.meBean = (MemberBean)session.getAttribute("loginfo");
		
		if (meBean != null) { //로그인한 정보가 남아있다면
			session.invalidate(); // 세션 삭제
		}
		return "redirect:/main.co";
	}
}
