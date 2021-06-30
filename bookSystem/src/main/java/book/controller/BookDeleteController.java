package book.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import bean.BookBean;
import bean.RentalBean;
import dao.BookDao;
import dao.RentalDao;

@Controller
public class BookDeleteController {
	private final String COMMAND = "bkDelete.bk";

	@Autowired
	@Qualifier(value = "bkDao")
	private BookDao bkDao;
	private BookBean bkBean;
	
	@Autowired
	@Qualifier(value = "reDao")
	private RentalDao reDao;
	private RentalBean reBean;
	


	private ModelAndView mav;

	public BookDeleteController() {
		this.bkBean = new BookBean();
		this.bkDao = new BookDao();
		this.mav = new ModelAndView();

	}

	@GetMapping(value = COMMAND)
	public ModelAndView doGet(@RequestParam(value = "bkSeq", required = true) int bkSeq,
			HttpServletRequest requeset) {

		// 업데이트할 내용
		String message = "도서 삭제번호 : " +  bkSeq;
		
		// 만약 회원이 먼저 삭제된 내역이 있으면 reRemark 컬럼에 값이 들어 있음
		// 단, 도서번호는 여러개가 있을 수도 있기에 list 컬렉션에 담음 
		List<RentalBean> lists = this.reDao.selectRemarkData(bkSeq);
		
		String newMessage = "";
		// 기존 remark 컬럼에 업데이트 할 내용 포함
		if (lists != null) {
			for (RentalBean bean : lists) {
				System.out.println("확인");
				System.out.println(bean.toString());
			if (bean.getReRemark() == null) { //reRemark 컬럼에 값이 null이라면
				newMessage = message;
			}else {// reRemark 컬럼에 값이 있다면
				newMessage = bean.getReRemark() + ", " + message;
			}
		}
		
		int cnt = -1;
		cnt = this.reDao.updateBookRemark(bkSeq, newMessage);
		
		cnt = -1; // 초기화 
		cnt = this.bkDao.deleteData(bkSeq);
		
		if (cnt>0) {
			System.out.println("정상적으로 삭제되었습니다.");
			mav.setViewName("redirect:/bkList.bk");
		}
		}

		return mav;
	}

}
