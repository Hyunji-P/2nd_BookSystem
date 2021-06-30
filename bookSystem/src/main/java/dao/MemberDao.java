package dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bean.MemberBean;
import bean.StatisticsDetail;

@Component("meDao")
public class MemberDao {
	private final String namespace = "nsMember.";

	@Autowired
	private SqlSession sql_session;
	
	private MemberBean mebean;

	// 2021-06-10_박현지 
	// 로그인 실행시 아이디와 패스워드가 DB에 있는지 찾아주는 메소드
	public MemberBean selectByIdPw(String meEmail, String mePassword) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("meEmail", meEmail);
		map.put("mePassword", mePassword);
		
		this.mebean = this.sql_session.selectOne(this.namespace + "selectByIdPw", map);
		
		return mebean;
	}

	// 2021-06-10_박현지 
	// 회원가입 담당 메소드
	public int insertData(MemberBean member) {
		int cnt = -1;
		cnt = this.sql_session.insert(this.namespace + "insertData", member);
		return cnt;
	}

	// 2021-06-10_박현지
	// 이메일 중복 검사 담당 메소드 
	public int duplCheckEmail(String meEmail) {
		int cnt = -1;
		cnt = this.sql_session.selectOne(this.namespace + "duplCheckEmail" , meEmail);
		return cnt;
	}

	// 2021-06-10_박현지
	// 회원 정보 수정에 해당하는 메소드
	public int updateData(MemberBean member) {
		int cnt = -1;
		cnt = this.sql_session.update(this.namespace + "updateData", member);
		return cnt;
	}

	// 2021-06-10_박현지
	// 이메일을 이용하여 회원 정보를 가져오는 메소드
	public MemberBean selectById(String meEmail) {
		this.mebean = this.sql_session.selectOne(this.namespace + "selectById" , meEmail);
		return mebean;
	}

	// 2021-06-10_박현지
	// 회원 목록 전체 조회
	
	// 2021-06-11_박현지
	// 페이징 처리 + 검색 기능 추가
	public List<MemberBean> selectAllData(int offset, int limit, String mode, String keyword) {
		// 페이징 처리
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		// 검색 기능
		Map<String, String> map = new HashMap<String, String>();
		map.put("mode", mode);
		map.put("keyword", keyword);
		
		
		List<MemberBean> lists = new ArrayList<MemberBean>();
		
		lists = this.sql_session.selectList(this.namespace + "selectAllData", map , rowBounds);
		return lists;
	}


	// 2021-06-14_박현지
	// 게시글 등 페이징 처리시 사용되는 총 건수를 구하는 메소드 
	public int selectTotalCount(String mode, String keyword) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("mode", mode);
		map.put("keyword", keyword);
		
		int totalCount = this.sql_session.selectOne(this.namespace + "selectTotalCount", map);
		return totalCount;
	}

	// 2021-06-15_박현지
	// 회원 탈퇴 
	public int deleteData(String meEmail) {
		int cnt = this.sql_session.delete(this.namespace + "deleteData", meEmail);
		return cnt;
	}

	// 2021-06-22_박현지
	// 회원별 월별 통계 화면에서 상세보기에 쓰일 갯수 구하기 
	public int memberDetailCount(String mode, String keyword, String email, int month) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("mode", mode);
		map.put("keyword", keyword);
		map.put("email", email);
		map.put("month", month);
		int cnt = this.sql_session.selectOne(this.namespace + "memberDetailCount", map);
		return cnt;
	}

	// 2021-06-22_박현지 
	// 회원별 월별 통계 화면에서 상세보기 리스트 
	public List<StatisticsDetail> selectDetailList(int month, String email, int offset, int limit, String mode, String keyword) {
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("mode", mode);
		map.put("keyword", keyword);
		map.put("email", email);
		map.put("month", month);
		
		return this.sql_session.selectList(this.namespace + "selectDetailList", map, rowBounds);
	}

	
	
}
