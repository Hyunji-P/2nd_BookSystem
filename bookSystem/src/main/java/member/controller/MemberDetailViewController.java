package member.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import bean.MemberBean;
import dao.MemberDao;

// 2021-06-10_박현지
// 회원 정보 상세 컨트롤러 

@Controller
public class MemberDetailViewController {
	private final String COMMAND = "/meDetailView.me";
	
	@Autowired
	@Qualifier(value = "meDao")
	private MemberDao meDao;
	
	private MemberBean meBean;
	
	private ModelAndView mav;
	
	public MemberDetailViewController() {
		this.meBean = new MemberBean();
		this.mav =  new ModelAndView();
		this.meDao = new MemberDao();
	}
	
	@GetMapping(value = COMMAND)
	public String doGet(
			@RequestParam(value = "meEmail") String meEmail,
			HttpServletRequest request) {
		
		this.meBean = this.meDao.selectById(meEmail);
		request.setAttribute("bean", meBean);
		return "meDetailView";
	}
}
