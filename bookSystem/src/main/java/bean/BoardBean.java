package bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoardBean {
	private int boSeq; // 게시글 번호
	private String boEmail; // 게시글 등록자
	private String boTitle; // 게시글 제목
	private String boContent; // 게시글 내용
	private String boRegDate; // 게시글 등록일
	private int boHits; // 게시글 조회수 
	
	/*화면단 변수*/
	private String writer; // 작성자
	
	/*
	 * 계층형 게시판을 위한 추가 필드
	 */
	
	private int boOriginSeq; // 원글 번호 , 답변을 단 원래 글(부모글)의 번호, boSeq == boOriginSeq
	private int boGroupOrd; // 원글 + 답글은 boOrginSeq로 묶임, 하나의 그룹으로 봤을 때 그룹 내 순서를 의미 
	private int boGroupLayer; // 원글에 대한 답글인지, 답글에 대한 답글인지 계층을 구분 
	
}
