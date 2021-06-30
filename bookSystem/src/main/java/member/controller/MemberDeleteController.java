package member.controller;

import java.util.List;

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
import dao.MemberDao;
import dao.RentalDao;

@Controller
public class MemberDeleteController {
	private final String COMMAND = "meDelete.me";

	@Autowired
	@Qualifier(value = "meDao")
	private MemberDao meDao;

	@Autowired
	@Qualifier(value = "reDao")
	private RentalDao reDao;

	private MemberBean meBean;

	private ModelAndView mav;

	public MemberDeleteController() {
		this.meBean = new MemberBean();
		this.mav = new ModelAndView();
		this.meDao = new MemberDao();
	}

	@GetMapping(value = COMMAND)
	public ModelAndView doGet(@RequestParam(value = "meEmail", required = true) String meEmail,
			HttpServletRequest requeset) {

		// 회원 삭제 시 대여 기록은 남아있어야함
		RentalDao reDao = new RentalDao();

		// 업데이트할 내용
		String message = "탈퇴 : " + meEmail;

		// 만약 도서가 먼저 삭제된 내역이 있으면 reRemark 컬럼에 값이 들어 있음
		// 단, 회원 이메일은 여러개 있을 수도 있기에 list 컬렉션에 담음
		List<RentalBean> lists = this.reDao.selectMemberRemarkData(meEmail);

		String newMessage = "";
		if (lists != null) {
			for (RentalBean bean : lists) {
				System.out.println("확인");
				System.out.println(bean.toString());
				if (bean.getReRemark() == null) { // reRemark 컬럼에 값이 null이라면
					newMessage = message;
				} else {// reRemark 컬럼에 값이 있다면
					newMessage = message + ", " + bean.getReRemark();
				}
			}

			int cnt = -1;
			cnt = this.reDao.updateRemark(meEmail, newMessage);

			if (cnt > 0) {
				System.out.println("대여 테이블 비고 컬럼 업데이트 성공");
			} else {
				System.out.println("업데이트 할 건이 없음");
			}

			cnt = -1;
			cnt = this.meDao.deleteData(meEmail);

			if (cnt > 0) {
				System.out.println("회원 탈퇴 성공");
				
				// 세션 삭제
				HttpSession session = requeset.getSession();
				session.invalidate();
				
				mav.setViewName("redirect:/main.co");
			} else {
				System.out.println("회원 탈퇴 실패");
				mav.setViewName("redirect:/meDetailView.me?meEmail=" + meEmail);
			}

		}
		return mav;

	}
}
