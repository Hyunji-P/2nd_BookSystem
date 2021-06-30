package member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.hibernate.validator.constraints.Mod10Check;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import bean.MemberBean;
import dao.MemberDao;

// 2021-06-10_박현지
// 회원가입 컨트롤러 

@Controller
public class MemberInsertController {
	private final String COMMAND = "/meInsert.me";

	@Autowired
	@Qualifier(value = "meDao")
	private MemberDao meDao;

	private MemberBean meBean;

	private ModelAndView mav;

	public MemberInsertController() {
		this.meBean = new MemberBean();
		this.mav = new ModelAndView();
		this.meDao = new MemberDao();
	}

	@GetMapping(value = COMMAND)
	public String doGet() {
		return "meInsert";
	}

	@PostMapping(value = COMMAND)
	public ModelAndView doPost(
			MemberBean member, 
			HttpServletRequest request) {

			int cnt = -1;

			cnt = this.meDao.insertData(member);

			if (cnt > 0) {
				System.out.println("회원 가입 성공");
				mav.setViewName("redirect:/main.co");
			} else {
				request.setAttribute("errMassege", "회원가입에 실패되었습니다.");
				this.mav.setViewName("meInsert");
			}

		return mav;
	}
}
