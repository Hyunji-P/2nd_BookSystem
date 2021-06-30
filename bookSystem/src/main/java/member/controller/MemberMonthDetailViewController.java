package member.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import bean.MemberBean;
import bean.StatisticsDetail;
import dao.MemberDao;
import utility.FlowParameters;
import utility.Paging;

@Controller
public class MemberMonthDetailViewController {
	private final String COMMAND = "/meMonthDetail.me";

	@Autowired
	@Qualifier(value = "meDao")
	private MemberDao meDao;

	private MemberBean meBean;

	private ModelAndView mav;

	public MemberMonthDetailViewController() {
		this.meBean = new MemberBean();
		this.mav = new ModelAndView();
		this.meDao = new MemberDao();
	}

	@GetMapping(value = COMMAND)
	public ModelAndView doGet(@RequestParam(value = "pageSize", required = false) String pageSize,
			@RequestParam(value = "pageNumber", required = false) String pageNumber,
			@RequestParam(value = "mode", required = false) String mode,
			@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "month", required = true) int month,
			@RequestParam(value = "email", required = true) String email, 
			HttpServletRequest request) {

		// 페이징 처리
		FlowParameters param = new FlowParameters(pageNumber, pageSize, mode, keyword);

		int totalCount = this.meDao.memberDetailCount(param.getMode(), "%" + param.getKeyword() + "%", email, month);

		String url = this.COMMAND;

		Paging pageInfo = new Paging(param.getPageNumber(), param.getPageSize(), totalCount, url, param.getMode(),
				param.getKeyword());

		List<StatisticsDetail> detailList = this.meDao.selectDetailList(month, email, pageInfo.getOffset(),
				pageInfo.getLimit(), pageInfo.getMode(), "%" + pageInfo.getKeyword() + "%");

		if (detailList != null) {
			// 목록 갯수
			mav.addObject("totalCount", totalCount);

			// 목록
			mav.addObject("detailList", detailList);

			// 페이징 관력 항목들
			mav.addObject("pagingHtml", pageInfo.getPagingHtml());

			// 필드 검색과 관련 항목들
			mav.addObject("mode", param.getMode());
			mav.addObject("keyword", param.getKeyword());

			// 파라미터 리스트 문자열 : 상세보기 , 수정 , 삭제 등에 사용됨
			mav.addObject("parameters", param.toString());
			
			// 필수 파라미터
			mav.addObject("month", month);
			mav.addObject("email", email);

			mav.setViewName("monthStatDetail");
		}

		return mav;
	}

}
