package rental.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import bean.MemberBean;
import bean.RentalBean;
import dao.RentalDao;

@Controller
public class RentDeleteController {
	private final String COMMAND = "/reDelete.re";

	@Autowired
	@Qualifier(value = "reDao")
	private RentalDao reDao;

	private RentalBean reBean;

	private ModelAndView mav;

	public RentDeleteController() {
		this.reBean = new RentalBean();
		this.reDao = new RentalDao();
		this.mav = new ModelAndView();

	}

	@GetMapping(value = COMMAND)
	public ModelAndView doGet(@RequestParam(value = "reSeq", required = true) int reSeq, HttpServletRequest request) {

		HttpSession session = request.getSession();
		MemberBean meBean = (MemberBean) session.getAttribute("loginfo"); // 회원 정보를 가져옴

		int cnt = -1;
		cnt = this.reDao.deleteData(reSeq);

		if (cnt > 0) {
			System.out.println("대여기록 삭제 성공");
		} else {
			System.out.println("대여기록 삭제 실패");
		}
		mav.setViewName("redirect:/reList.re?reEmail=" + meBean.getMeEmail());
		return mav;
	}

}
