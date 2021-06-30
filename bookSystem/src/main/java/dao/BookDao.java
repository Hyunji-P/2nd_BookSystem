package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bean.BookBean;
import bean.BookMonth;
import bean.StatisticsDetail;

@Component("bkDao")
public class BookDao {

	private final String namespace = "nsBook.";

	@Autowired
	private SqlSession sql_session;

	// 2021-06-14_박현지
	// 도서 등록 메소드
	public int insertData(BookBean bkbean) {
		int cnt = this.sql_session.insert(this.namespace + "insertData", bkbean);
		return cnt;
	}

	// 2021-06-14_박현지
	// 도서 게시글 전체 수
	public int selectTotalCount(String mode, String keyword) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("mode", mode);
		map.put("keyword", keyword);

		int cnt = this.sql_session.selectOne(this.namespace + "selectTotalCount", map);
		return cnt;
	}

	// 2021-06-14_박현지
	// 도서 전체 목록
	public List<BookBean> selectAllData(int offset, int limit, String mode, String keyword) {
		RowBounds rowBounds = new RowBounds(offset, limit);

		Map<String, String> map = new HashMap<String, String>();
		map.put("mode", mode);
		map.put("keyword", keyword);

		List<BookBean> lists = this.sql_session.selectList(this.namespace + "selectAllData", map, rowBounds);
		return lists;
	}

	// 2021-06-14_박현지
	// 도서 번호에 해당하는 도서 bean객체를 구하는 메소드
	public BookBean selectOneData(int bkSeq) {
		BookBean bkbean = this.sql_session.selectOne(this.namespace + "selectOneData", bkSeq);
		return bkbean;
	}

	// 2021-06-14_박현지
	// 도서 번호에 해당하는 bean 객체를 수정
	public int updateData(BookBean bkbean) {
		int cnt = -1;
		cnt = this.sql_session.update(this.namespace + "updateData", bkbean);
		return cnt;
	}

	// 2021-06-16_박현지
	// 도서 번호에 해당하는 데이터 삭제
	public int deleteData(int bkSeq) {
		int cnt = -1;
		cnt = this.sql_session.delete(this.namespace + "deleteData", bkSeq);
		return cnt;
	}

	// 2021-06-22_박현지
	// 도서 월별 상세보기에 사용될 건수
	public int bookDetailCount(String mode, String keyword, int bookNumber, int month) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("mode", mode);
		map.put("keyword", keyword);
		map.put("bookNumber", bookNumber);
		map.put("month", month);
		int cnt = this.sql_session.selectOne(this.namespace + "bookDetailCount", map);
		return cnt;
	}

	// 2021-06-22_박현지
	// 도서 월별 상세보기 리스트
	public List<StatisticsDetail> selectDetailList(int month, int bookNumber, int offset, int limit, String mode,
			String keyword) {
		RowBounds rowBounds = new RowBounds(offset, limit);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("mode", mode);
		map.put("keyword", keyword);
		map.put("bookNumber", bookNumber);
		map.put("month", month);

		return this.sql_session.selectList(this.namespace + "selectDetailList", map, rowBounds);
	}

}
