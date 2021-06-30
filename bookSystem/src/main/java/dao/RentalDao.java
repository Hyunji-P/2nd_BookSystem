package dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bean.BookMonth;
import bean.ListAdminBean;
import bean.RentalBean;
import bean.StatisticMonth;

@Component("reDao")
public class RentalDao {
	private final String namespace = "nsRental.";

	@Autowired
	private SqlSession sql_session;

	private RentalBean rebean;

	// 2021-06-15_박현지
	// 도서 대여 시 대여 일자의 중복을 체크한다.
	// 모든 도서는 단 1권만 있다고 가정한다.
	public int selectDuplDate(String reStartDate, String reEndDate, int reBookNumber) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("reStartDate", reStartDate);
		map.put("reEndDate", reEndDate);
		map.put("reBookNumber", reBookNumber);

		int cnt = this.sql_session.selectOne(this.namespace + "selectDuplDate", map);
		return cnt;
	}

	// 2021-06-15_박현지
	// 도서 대여 insert 처리
	public int insertData(RentalBean reBean) {
		int cnt = -1;
		cnt = this.sql_session.insert(this.namespace + "insertData", reBean);
		return cnt;
	}

	// 2021-06-15_박현지
	// 회원 대여 목록에서 이메일 별 대여 건수
	public int selectTotalCount(String reEmail) {

		int cnt = -1;
		cnt = this.sql_session.selectOne(this.namespace + "selectTotalCount", reEmail);

		return cnt;
	}

	// 2021-06-15_박현지
	// 회원 대여 목록
	public List<RentalBean> selectAllData(int offset, int limit, String reEmail) {
		RowBounds rowBounds = new RowBounds(offset, limit);

		List<RentalBean> lists = this.sql_session.selectList(this.namespace + "selectAllData", reEmail, rowBounds);

		return lists;
	}

	// 2021-06-15_박현지
	// 대여 상세 보기
	public RentalBean selectOneData(int reSeq) {
		RentalBean bean = this.sql_session.selectOne(this.namespace + "selectOneData", reSeq);
		return bean;
	}

	// 2021-06-15_박현지
	// 대여 기록 삭제
	public int deleteData(int reSeq) {
		int cnt = this.sql_session.delete(this.namespace + "deleteData", reSeq);
		return cnt;
	}

	// 2021-06-15_박현지
	// 회원이 삭제 되어도 대여 기록은 남아 있어야함
	// remark 비고 컬럼 업데이트
	public int updateRemark(String meEmail, String reRemark) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("reEmail", meEmail);
		map.put("reRemark", reRemark);

		int cnt = this.sql_session.update(this.namespace + "updateRemark", map);
		return cnt;
	}

	// 2021-06-16_박현지
	// 관리자가 도서 삭제를 하기전 remark 컬럼에 값이 있는지 확인
	public List<RentalBean> selectRemarkData(int reBookNumber) {
		List<RentalBean> lists = new ArrayList<RentalBean>();
		lists = this.sql_session.selectList(this.namespace + "selectRemarkData", reBookNumber);
		return lists;
	}

	// 2021-06-15_박현지
	// 도서가 삭제 되어도 대여 기록이 남아 있어야함
	// remark 비고 컬럼 업데이트
	public int updateBookRemark(int bkSeq, String newMessage) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bkSeq", bkSeq);
		map.put("newMessage", newMessage);

		int cnt = this.sql_session.update(this.namespace + "updateBookRemark", map);
		return cnt;
	}

	// 2021-06-16_박현지
	// 회원이 탈퇴 하기전 대여 테이블에 remark 컬럼에 값이 있는지 확인
	public List<RentalBean> selectMemberRemarkData(String meEmail) {
		List<RentalBean> lists = new ArrayList<RentalBean>();
		lists = this.sql_session.selectList(this.namespace + "selectMemberRemarkData", meEmail);
		return lists;
	}

	// 2021-06-16_박현지
	// 수정 처리 시 get방식으로 접근할 때 날짜는 yyyy-mm-dd 형식으로 가져와야함
	// input 태그에 value값 셋팅 시 하이픈 포함
	public RentalBean getUpdateData(int reSeq) {
		RentalBean bean = this.sql_session.selectOne(this.namespace + "getUpdateData", reSeq);
		return bean;
	}

	// 2021-06-16_박현지
	// 대여일 수정
	public int updateData(RentalBean reBean) {
		int cnt = -1;
		cnt = this.sql_session.update(this.namespace + "updateData", reBean);
		return cnt;
	}

	// 2021-06-16_박현지
	// 관리자용 대여 리스트 총 건수
	public int adminListTotal(String mode, String keyword) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("mode", mode);
		map.put("keyword", keyword);

		int cnt = -1;
		cnt = this.sql_session.selectOne(this.namespace + "adminListTotal", map);
		return cnt;
	}

	// 2021-06-16_박현지
	// 관리자용 대여 리스트
	public List<ListAdminBean> adminListAll(int offset, int limit, String mode, String keyword) {
		RowBounds rowBounds = new RowBounds(offset, limit);

		Map<String, String> map = new HashMap<String, String>();
		map.put("mode", mode);
		map.put("keyword", keyword);

		System.out.println(mode);
		System.out.println(keyword);

		List<ListAdminBean> lists = this.sql_session.selectList(this.namespace + "adminListAll", map, rowBounds);
		return lists;
	}

	// 2021-06-21_박현지
	// 회원별 월별 통계 리스트(신규)
	public List<StatisticMonth> selectMonthStat(int offset, int limit, String mode, String keyword) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("mode", mode);
		map.put("keyword", keyword);

		RowBounds rowBounds = new RowBounds(offset, limit);

		return this.sql_session.selectList(this.namespace + "selectMonthStat", map, rowBounds);
	}

	// 2021-06-21_박현지
	// 회원별 월별 통계 리스트 건수
	public List<StatisticMonth> statMonthCount(String mode, String keyword) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("mode", mode);
		map.put("keyword", keyword);

		return this.sql_session.selectList(this.namespace + "statMonthCount", map);

	}

	// 2021-06-22_박현지
	// 도서 월별 통계 건수 구하기
	public List<BookMonth> bookMonthCount(String mode, String keyword) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("mode", mode);
		map.put("keyword", keyword);

		return this.sql_session.selectList(this.namespace + "bookMonthCount", map);
	}

	// 2021-06-22_박현지
	// 도서 월별 통계 리스트
	public List<BookMonth> selectBookMonth(int offset, int limit, String mode, String keyword) {
		RowBounds rowBounds = new RowBounds(offset, limit);

		Map<String, String> map = new HashMap<String, String>();
		map.put("mode", mode);
		map.put("keyword", keyword);

		return this.sql_session.selectList(this.namespace + "selectBookMonth", map, rowBounds);

	}

	// 2021-06-23_박현지
	// 대여 테이블에 해당 이메일이 중복되는지 체크한다.
	// 중복 되면 기존 통계 데이터에서 누적하며, 중복이 되지 않으면 새로운 유저를 추가한다. 
	public int emailDuplCheck(String email) {
		return this.sql_session.selectOne(this.namespace + "emailDuplCheck", email);
	}

}
