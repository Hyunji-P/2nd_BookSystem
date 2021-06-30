package board.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import bean.BoardBean;
import bean.MemberBean;
import dao.BoardDao;

@Controller
public class BoardInsertController {
	private final String COMMAND = "/boInsert.bo";
	
	@Autowired
	@Qualifier(value = "boDao")
	private BoardDao boDao;
	
	private BoardBean boBean;
	
	private MemberBean meBean;
	
	private ModelAndView mav;
	
	public BoardInsertController() {
		this.boBean = new BoardBean();
		this.boDao =  new BoardDao();
		this.mav = new ModelAndView();
		
	}
	
	@GetMapping(value = COMMAND)
	public String doGet() {
		return "boInsert";
	}
	
	@PostMapping(value = COMMAND)
	public ModelAndView doPost(
			BoardBean boBean,
			HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		
		this.meBean = (MemberBean) session.getAttribute("loginfo");
		
		if (meBean != null) {
			boBean.setBoEmail(meBean.getMeEmail()); // 이메일 셋팅 
		}
		
		System.out.println("확인 ==> " + boBean.toString()); // bean 확인 
		
		int cnt = -1;
		cnt = this.boDao.insertData(boBean);
		
		if (cnt > 0) { // 정상 등록 처리 되면 
			mav.setViewName("redirect:/boList.bo");
		}else {
			session.setAttribute("message", "게시글 등록에 실패하였습니다.");
			mav.setViewName("boInsert");
		}
		
		return mav;
	}
	
}
