package board.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import bean.BoardBean;
import dao.BoardDao;

@Controller
public class BoardDeleteController {
	private final String COMMAND = "boDelete.bo";

	@Autowired
	@Qualifier(value = "boDao")
	private BoardDao boDao;

	private BoardBean boBean;

	private ModelAndView mav;

	public BoardDeleteController() {
		this.boBean = new BoardBean();
		this.boDao = new BoardDao();
		this.mav = new ModelAndView();

	}

	@GetMapping(value = COMMAND)
	public ModelAndView doGet(@RequestParam(value = "boSeq", required = true) int boSeq, HttpServletRequest request) {

		this.boBean = this.boDao.selectOneData(boSeq); // 해당 게시글 가져오기

		// 삭제 로직
		// 1. 원글이 삭제 되는 경우 : 원글 + 답글 + 답답글 모두 삭제 → boOriginSeq로 삭제
		// 2. 답글이 삭제 되는 경우 : 답글 + 답답글 모두 삭제 → boOriginSeq는 같고 boGroupOrd가 같은 경우 삭제
		// 3. 답답글이 삭제 되는 경우 : 답답글만 삭제 → boSeq로 삭제

		int cnt = -1;
		HttpSession session = request.getSession(); // 메세지를 담을 세션

		if (boBean.getBoGroupOrd() == 0) { // 그룹내 컬럼이 0이면 원글만 작성된 경우
			cnt = this.boDao.deleteOriginData(boBean.getBoOriginSeq());

			if (cnt > 0) { // 정상적으로 삭제가 된 경우
				session.setAttribute("err_message", "원글이 삭제되었습니다. (※ 해당 원글에 달린 답글도 모두 삭제됩니다.)");
			}
		} else if (boBean.getBoGroupOrd() > 0 && boBean.getBoGroupLayer() == 1) { // 원글에 답글이 작성된 경우
			cnt = this.boDao.deleteReplyData(boBean.getBoOriginSeq(), boBean.getBoGroupOrd()); 
			
			if (cnt > 0) { // 정상적으로 삭제가 된 경우
				session.setAttribute("err_message", "답글이 삭제되었습니다. (※ 해당 답글에 달린 답글도 모두 삭제됩니다.)");
			}
		}else { // 답글에 답글이 작성된 경우 
			cnt = this.boDao.deleteReplyData(boSeq); 
			
			if (cnt > 0) { // 정상적으로 삭제가 된 경우
				session.setAttribute("err_message", "답글이 삭제되었습니다.");
			}
		}
		mav.setViewName("redirect:/boList.bo");
		
		return mav;
	}

}
