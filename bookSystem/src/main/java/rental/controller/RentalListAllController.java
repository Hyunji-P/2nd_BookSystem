package rental.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import bean.BookBean;
import bean.RentalBean;
import dao.BookDao;
import dao.RentalDao;
import utility.FlowParameters;
import utility.Paging;

@Controller
public class RentalListAllController {
	private final String COMMAND = "reList.re";
	
	@Autowired
	@Qualifier(value = "reDao")
	private RentalDao reDao;
	
	private RentalBean reBean;
	
	private ModelAndView mav;
	
	public RentalListAllController() {
		this.reBean = new RentalBean();
		this.reDao =  new RentalDao();
		this.mav = new ModelAndView();
		
	}
	
	@GetMapping(value = COMMAND)
	public ModelAndView doGet(
			@RequestParam(value = "pageSize", required = false) String pageSize,
			@RequestParam(value = "pageNumber", required = false) String pageNumber,
			@RequestParam(value = "reEmail", required = true) String reEmail,
			HttpServletRequest request
			) {
		
		// 페이징 처리 
		FlowParameters param = new FlowParameters(pageNumber, pageSize, "", "");
		
		// 대여 목록 전체
		int totalCount = this.reDao.selectTotalCount(reEmail);
		
		String url = this.COMMAND;
		
		Paging pageInfo = new Paging(pageNumber, pageSize, totalCount, url, "", "");
		
		List<RentalBean> lists = this.reDao.selectAllData(
				pageInfo.getOffset(),
				pageInfo.getLimit(),
				reEmail);
		
		if (lists != null) { // 목록에 잘 담겼으면 목록 페이지로 이동
			// 목록 갯수
			mav.addObject("totalCount", totalCount);
			
			// 목록
			mav.addObject("lists", lists);
			
			// 페이징 관련 
			mav.addObject("pagingHtml", pageInfo.getPagingHtml());
			
			// 파라미터 리스트 문자열 : 상세보기, 수정 , 삭제 등에 사용
			mav.addObject("parameters", param.toString());
			
			mav.setViewName("reListAll");
		}
		
		return mav;
	}
	
	
}
