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
// 이메일 중복 검사 컨트롤러 

@Controller
public class MemberDuplCheckController {
	private final String COMMAND = "/meDuplCheck.me";
	
	@Autowired
	@Qualifier(value = "meDao")
	private MemberDao meDao;
	
	private MemberBean meBean;
	
	private ModelAndView mav;
	
	public MemberDuplCheckController() {
		this.meBean = new MemberBean();
		this.mav =  new ModelAndView();
		this.meDao = new MemberDao();
	}
	
	@GetMapping(value = COMMAND)
	public ModelAndView doGet(
			@RequestParam(value = "meEmail") String meEmail,
			HttpServletRequest request
			) {
		
		int cnt = -1;
		
		cnt = this.meDao.duplCheckEmail(meEmail);
		
		if (cnt > 0) { // 중복된 이메일이 테이블에 존재하면 cnt ++ , 따라서 0보다 크면 사용 불가능 
			request.setAttribute("dupl_Check_Message", "사용 불가능한 이메일입니다.");
			request.setAttribute("isCheck", false);
			
		}else { // 중복된 이메일이 없음, 사용 가능한 이메일 
			request.setAttribute("dupl_Check_Message", "사용 가능한 이메일입니다.");
			request.setAttribute("isCheck", true);
		}
		mav.setViewName("meDuplCheck");
		
		return mav;
	}
	
}
