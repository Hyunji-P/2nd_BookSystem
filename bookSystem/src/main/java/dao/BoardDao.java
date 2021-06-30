package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bean.BoardBean;

@Component("boDao")
public class BoardDao {

	private final String namespace = "nsBoard.";

	@Autowired
	private SqlSession sql_session;

	// 2021-06-24_박현지 
	// 게시글 전체 건수 가져오기 
	public int selectTotalCount(String mode, String keyword) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("mode", mode);
		map.put("keyword", keyword);
		
		return this.sql_session.selectOne(this.namespace + "selectTotalCount", map);
	}

	// 2021-06-24_박현지
	// 게시글 데이터 가져오기
	public List<BoardBean> selectAllData(int offset, int limit, String mode, String keyword) {
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("mode", mode);
		map.put("keyword", keyword);
		
		return this.sql_session.selectList(this.namespace + "selectAllData", map, rowBounds);
	}

	// 2021-06-25_박현지
	// 게시글 등록
	public int insertData(BoardBean boBean) {
		return this.sql_session.insert(this.namespace + "insertData", boBean);
	}

	// 2021-06-25_박현지
	// 게시글 상세보기 
	public BoardBean selectOneData(int boSeq) {
		return this.sql_session.selectOne(this.namespace + "selectOneData", boSeq);
	}

	// 2021-06-25_박현지
	// 조회수 업데이트 
	public int updateHit(int boHits, int boSeq) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("boHits", boHits);
		map.put("boSeq", boSeq);
		return this.sql_session.update(this.namespace + "updateHit", map);
	}

	// 2021-06-28_박현지
	// 원글에 답글 달기 
	public int insertFromOriginToReply(BoardBean boBean) {
		
		return this.sql_session.insert(this.namespace + "insertFromOriginToReply", boBean);
	}

	// 2021-06-28_박현지
	// 원글에 이미 답글이 있는 경우, 답글의 순서를 지정 
	public int updateFromOriginToReply(BoardBean prevBoard) {
		return this.sql_session.update(this.namespace + "updateFromOriginToReply", prevBoard);
	}


	// 2021-06-28_박현지
	// 원글에 이미 답글이 등록 되어있는지 확인 
	public int selectReplyCount(int boOriginSeq) {
		return this.sql_session.selectOne(this.namespace + "selectReplyCount", boOriginSeq);
	}

	// 2021-06-28_박현지
	// 방금전에 데이터가 insert 된 데이터 가져오기
	public BoardBean selectCurrData(int boOriginSeq) {
		return this.sql_session.selectOne(this.namespace + "selectCurrData", boOriginSeq);
	}

	// 2021-06_29_박현지
	// 현재 insert 된 데이터의 이전 데이터를 가져오기
	public BoardBean selectPrevData(int boOriginSeq) {
		return this.sql_session.selectOne(this.namespace + "selectPrevData", boOriginSeq);
	}

	// 2021-06-29_박현지
	// 답글에 답글 등록하기 
	public int insertFromReplyToReply(BoardBean boBean) {
		return this.sql_session.insert(this.namespace + "insertFromReplyToReply", boBean);
	}

	// 2021-06-29_박현지
	// 답글에 답글이 등록된 경우 답글의 순서를 지정 
	public int updateFromReplyToReply(BoardBean currBoard) {
		return this.sql_session.update(this.namespace + "updateFromReplyToReply", currBoard);
	}

	// 2021-06-29_박현지
	// 게시글 수정
	public int updateData(BoardBean boBean) {
		return this.sql_session.update(this.namespace + "updateData", boBean);
	}

	// 2021-06-29_박현지
	// 원글 게시글 삭제 
	public int deleteOriginData(int boOriginSeq) {
		return this.sql_session.delete(this.namespace + "deleteOriginData", boOriginSeq);
	}

	// 2021-06-29_박현지
	// 원글에 달린 답글 삭제
	public int deleteReplyData(int boOriginSeq, int boGroupOrd) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("boOriginSeq", boOriginSeq); 
		map.put("boGroupOrd", boGroupOrd);
		
		
		return this.sql_session.delete(this.namespace + "delFromOriToRepl", map);
	}

	// 2021-06-29_박현지
	// 답글에 달린 답글 삭제
	public int deleteReplyData(int boSeq) {
		return this.sql_session.delete(this.namespace + "delFromReplToRepl", boSeq);
	}

	// 2021-06-30_박현지 
	// 그룹 내 두번째 최댓값 가져오기 = 답글을 달기 전 입력된 데이터 
	public BoardBean replyPrevData(int boOriginSeq, int boGroupOrd) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("boOriginSeq", boOriginSeq);
		map.put("boGroupOrd", boGroupOrd);
		
		return this.sql_session.selectOne(this.namespace + "replyPrevData", map);
	}

}
