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
import bean.MemberBean;
import dao.BoardDao;
import dao.MemberDao;
import utility.FlowParameters;

@Controller
public class FromReplyToReplyController {
	private final String COMMAND = "/boFromReplToRepl.bo";

	@Autowired
	@Qualifier(value = "boDao")
	private BoardDao boDao;

	private BoardBean boBean;

	@Autowired
	@Qualifier(value = "meDao")
	private MemberDao meDao;

	private MemberBean meBean;

	private ModelAndView mav;

	public FromReplyToReplyController() {
		this.boBean = new BoardBean();
		this.boDao = new BoardDao();
		this.mav = new ModelAndView();

	}

	@GetMapping(value = COMMAND)
	public ModelAndView doGet(
			@RequestParam(value = "boSeq", required = true) int boSeq,
			@RequestParam(value = "pageSize", required = false) String pageSize,
			@RequestParam(value = "pageNumber", required = false) String pageNumber,
			@RequestParam(value = "mode", required = false) String mode,
			@RequestParam(value = "keyword", required = false) String keyword, HttpServletRequest request) {

		FlowParameters param = new FlowParameters(pageNumber, pageSize, mode, keyword);

		this.boBean = this.boDao.selectOneData(boSeq); // 게시글 정보

		mav.addObject("parameters", param.toString()); // 페이징 파라미터 정보 바인딩
		mav.addObject("boBean", boBean); // 게시글 정보 바인딩
		mav.setViewName("boFromReplToRepl");
		return mav;
	}

	@PostMapping(value = COMMAND)
	public ModelAndView doPost(
			BoardBean boBean, 
			HttpServletRequest request) {

		// 회원 정보
		HttpSession session = request.getSession();

		this.meBean = (MemberBean) session.getAttribute("loginfo");

		boBean.setBoEmail(meBean.getMeEmail()); // 이메일 셋팅
		boBean.setWriter(meBean.getMeName()); // 작성자 셋팅


		// 가장 처음에 달린 답글 데이터 가져오기
		BoardBean firstReply = this.boDao.selectOneData(boBean.getBoSeq());
		boBean.setBoOriginSeq(firstReply.getBoOriginSeq()); // 원글 고정 
		boBean.setBoGroupOrd(firstReply.getBoGroupOrd()); // 그룹내 순서 고정 
		
		int cnt = -1;
		cnt = this.boDao.insertFromReplyToReply(boBean); // 답글에 답글 등록
		
		// 방금전 게시글 가져오기 
		// boGroupLayer 에 + 1 증가 
		BoardBean currBoard = this.boDao.selectCurrData(boBean.getBoOriginSeq()); // 현재 등록된 게시글 데이터 가져오기
		System.out.println("방금 전 insert된 데이터 : " + currBoard.toString());
		// 게시글 순서의 두번째 최댓값 구하기 = 현재 등록된 데이터의 이전 데이터를 의미 
		BoardBean prevBoard = this.boDao.replyPrevData(boBean.getBoOriginSeq(), boBean.getBoGroupOrd());  
		System.out.println("이전 insert된 데이터 : " + prevBoard.toString());
		
		
		if (currBoard.getBoGroupOrd() == prevBoard.getBoGroupOrd()) { // 현재 답글의 그룹과 이전 답글의 그룹 순서가 동일 하다면, 그룹 계층을 분리
			int newBoGroupLayer = prevBoard.getBoGroupLayer() + 1; // 이전 게시물의 답글 그룹 순서 + 1
			
			System.out.println("확인 : " + newBoGroupLayer);
			currBoard.setBoGroupLayer(newBoGroupLayer);
			
			cnt = -1;
			cnt = this.boDao.updateFromReplyToReply(currBoard); // 원글의 그룹 번호를 수정 
		}

		if (cnt > 0) { // 정상 등록 되었으면
			mav.setViewName("redirect:/boList.bo");
		}
		return mav;
	}

}
