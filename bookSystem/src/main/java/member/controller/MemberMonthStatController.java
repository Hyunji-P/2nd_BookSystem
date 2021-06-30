package member.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.swing.plaf.synth.SynthSpinnerUI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import bean.MemberBean;
import bean.RentalBean;
import bean.ResultStatisticMonth;
import bean.StatisticMonth;
import dao.MemberDao;
import dao.RentalDao;
import utility.FlowParameters;
import utility.Paging;

@Controller
public class MemberMonthStatController {
	private final String COMMAND = "/meMonthStat.me";

	@Autowired
	@Qualifier(value = "meDao")
	private MemberDao meDao;

	private MemberBean meBean;

	@Autowired
	@Qualifier(value = "reDao")
	private RentalDao reDao;

	private RentalBean reBean;

	private ModelAndView mav;

	public MemberMonthStatController() {
		this.meBean = new MemberBean();
		this.mav = new ModelAndView();
		this.meDao = new MemberDao();
	}

	@GetMapping(value = COMMAND)
	public ModelAndView doGet(@RequestParam(value = "pageSize", required = false) String pageSize,
			@RequestParam(value = "pageNumber", required = false) String pageNumber,
			@RequestParam(value = "mode", required = false) String mode,
			@RequestParam(value = "keyword", required = false) String keyword, HttpServletRequest request) {

		// 페이징 처리
		FlowParameters param = new FlowParameters(pageNumber, pageSize, mode, keyword);

		List<StatisticMonth> listSize = this.reDao.statMonthCount(param.getMode(), "%" + param.getKeyword() + "%");
		int totalCount = listSize.size(); // 리스트 갯수

		String url = COMMAND;

		Paging pageInfo = new Paging(param.getPageNumber(), param.getPageSize(), totalCount, url, param.getMode(),
				"%" + param.getKeyword() + "%");

		// hearder 부분
		List<String> header = new ArrayList<String>();

		header.add("이메일");

		// 1월 ~ 12월까지 List에 데이터 넣기
		for (int i = 1; i <= 12; i++) {
			header.add(i + "월");
		}

		mav.addObject("header", header);

		// 내용물
		// 1 (월) = 2 (건) 형식으로 담김
		Map<Integer, Integer> contentMap = new HashMap<Integer, Integer>();

		// 초기화
		for (int i = 1; i < header.size(); i++) {
			contentMap.put(i, 0);
		}

		// 쿼리 조회
		List<StatisticMonth> sqlList = this.reDao.selectMonthStat(pageInfo.getOffset(), pageInfo.getLimit(),
				pageInfo.getMode(), pageInfo.getKeyword());

		// 이메일 + 1월 ~ 12월
		List<ResultStatisticMonth> resultList = new ArrayList<ResultStatisticMonth>();
		ResultStatisticMonth result = null;

		String temp = ""; // 이메일을 임시 저장할 변수
		for (int i = 0; i < sqlList.size(); i++) {
			StatisticMonth item = sqlList.get(i); // 반복되면서 변하는 객체

			if (i == 0) {
				temp = item.getEmail();
				result = new ResultStatisticMonth();
				result.setEmail(temp); // 이메일 셋팅
				result.setContentMap(new HashMap<Integer, Integer>(contentMap)); // 위에서 contenMap 데이터를 0으로 초기화 해주었음 → 객체 생성
				result.getContentMap().put(item.getMonth(),item.getCount());

				result.setSumCount(item.getCount());

			} else {
				if (temp.equals(item.getEmail())) {
					result.getContentMap().put(item.getMonth(),result.getContentMap().get(item.getMonth()) + item.getCount());

					result.setSumCount(result.getSumCount() + item.getCount());
				} else {
					temp = item.getEmail();
					resultList.add(result); // 기존까지 추가된 데이터 넣기

					result = new ResultStatisticMonth();

					result.setEmail(item.getEmail()); // 이메일 셋팅
					result.setContentMap(new HashMap<Integer, Integer>(contentMap));
					result.getContentMap().put(item.getMonth(),
							result.getContentMap().get(item.getMonth()) + item.getCount());

					result.setSumCount(item.getCount());
				}

			}
			if (i == sqlList.size() - 1) {
				resultList.add(result);
			}

		}

		// 목록 갯수
		mav.addObject("totalCount", totalCount);

		// 목록

		// 페이징 관련
		mav.addObject("pagingHtml", pageInfo.getPagingHtml());

		// 필드 검색과 관련 항목
		mav.addObject("mode", param.getMode());
		mav.addObject("keyword", param.getKeyword());

		// 파라미터 리스트 문자열 : 상세보기, 수정 , 삭제 등에 사용
		mav.addObject("parameters", param.toString());

		mav.addObject("resultList", resultList);
		mav.setViewName("monthStat");

		return mav;
	}

}
