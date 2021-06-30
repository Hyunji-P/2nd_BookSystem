package member.controller;

import javax.servlet.http.HttpServletRequest;

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
// 회원 정보 수정 컨트롤러 

@Controller
public class MemberUpdateController {
	private final String COMMAND = "/meUpdate.me";
	
	@Autowired
	@Qualifier(value = "meDao")
	private MemberDao meDao;
	
	private MemberBean meBean;
	
	private ModelAndView mav;
	
	public MemberUpdateController() {
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
		return "meUpdate";
	}
	
	@PostMapping(value = COMMAND)
	public ModelAndView doPost(
			MemberBean member,
			HttpServletRequest request
			) {
		
		int cnt = -1;
		
		cnt = this.meDao.updateData(member);
		
		if (cnt > 0) {
			System.out.println("회원 수정 성공");
			mav.setViewName("redirect:/main.co");
		}else {
			request.setAttribute("errMessage", "회원 수정에 실패되었습니다.");
			request.setAttribute("bean", member);
			this.mav.setViewName("meUpdate");
		}
		
		return mav;
	}
}
