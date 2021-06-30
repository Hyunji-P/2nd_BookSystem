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

import bean.ListAdminBean;
import bean.RentalBean;
import dao.RentalDao;
import utility.FlowParameters;
import utility.Paging;

@Controller
public class RentalListAdminController {
	private final String COMMAND = "/reListAdmin.re";
	
	@Autowired
	@Qualifier(value = "reDao")
	private RentalDao reDao;
	
	private RentalBean reBean;
	
	private ModelAndView mav;
	
	public RentalListAdminController() {
		this.reBean = new RentalBean();
		this.reDao =  new RentalDao();
		this.mav = new ModelAndView();
		
	}
	
	@GetMapping(value = COMMAND)
	public ModelAndView doGet(
			@RequestParam(value = "pageSize", required = false) String pageSize,
			@RequestParam(value = "pageNumber", required = false) String pageNumber,
			@RequestParam(value = "mode", required = false) String mode,
			@RequestParam(value = "keyword", required = false) String keyword,
			HttpServletRequest request
			) {
		
		// 페이징 처리 
		FlowParameters param = new FlowParameters(pageNumber, pageSize, mode, keyword);
		System.out.println(mode);
		System.out.println(keyword);
		// 총 게시글 건수
		int totalCount = this.reDao.adminListTotal(param.getMode(), "%" + param.getKeyword() + "%");
		
		String url = COMMAND;
		Paging pageInfo = new Paging(pageNumber, pageSize, totalCount, url, param.getMode(), param.getKeyword());
		
		List<ListAdminBean> lists = this.reDao.adminListAll(
				pageInfo.getOffset(),
				pageInfo.getLimit(),
				param.getMode(),
				"%" + param.getKeyword() + "%"
		);
		
		System.out.println(lists.toString());
		
		if (lists != null) { // 목록에 잘 담겼으면 목록 페이지로 이동
			// 목록 갯수
			mav.addObject("totalCount", totalCount);
			
			// 목록
			mav.addObject("lists", lists);
			
			// 페이징 관련 
			mav.addObject("pagingHtml", pageInfo.getPagingHtml());
			
			// 필드 검색과 관련 항목
			mav.addObject("mode", param.getMode());
			mav.addObject("keyword", param.getKeyword());
			
			// 파라미터 리스트 문자열 : 상세보기, 수정 , 삭제 등에 사용
			mav.addObject("parameters", param.toString());
			
			mav.setViewName("reListAdmin");
		}
		return mav;
	}
	

}
