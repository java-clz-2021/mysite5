package com.javaex.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaex.dao.BoardDao;
import com.javaex.vo.BoardVo;

@Service
public class BoardService {

	@Autowired
	private BoardDao boardDao;
	
	
	// 게시판 페이징 연습용 리스트
	public Map<String, Object> getList2(int crtPage, String keyword){
		System.out.println("[BoardService.getList2()]");
		
		//////////////////////////////////////////
		// 리스트 가져오기
		//////////////////////////////////////////
		int listCnt = 10;
		
		//crtPage 계산(- 값일때 1page 처리)
		crtPage = (crtPage > 0) ? crtPage : (crtPage=1);
		
		
		//시작번호 계산하기
		int startRnum = (crtPage-1)*listCnt+1;
		
		//끝번호 계산하기
		int endRnum = (startRnum+listCnt)-1;   //startRnum*listCnt
		
		
		List<BoardVo> boardList = boardDao.selectList2(startRnum, endRnum, keyword);
		
		
		//////////////////////////////////////////
		// 페이징 계산
		//////////////////////////////////////////
				
		//전체글 갯수
		int totalCount = boardDao.selectTotalCnt(keyword);
		System.out.println(totalCount);
		
		
		//페이지당 버튼 갯수
		int pageBtnCount = 5;
		
		
		//마지막 버튼 번호
		// 1  --> 1~5
		// 2  --> 1~5
		// 3  --> 1~5
		// 6  --> 6~10
		// 7  --> 6~10
		// 10 --> 6~10
		int endPageBtnNo =(int)Math.ceil((crtPage/(double)pageBtnCount)) * pageBtnCount;
		
		//시작 버튼 번호
		int startPageBtnNo = endPageBtnNo - (pageBtnCount-1);
		                     
		//다음 화살표 표현 유무
		boolean next = false;
		if((endPageBtnNo * listCnt) < totalCount) {
			next = true;
		}else {
			//다음 화살표 버튼이 없을때  endPageBtnNo 를 다시 계산해야된다.
			//전체글갯수/한페지의 글갯수 
			// 127 / 10.0   12.1  -->올림 13.0  --> 정수형 변환 13
			endPageBtnNo = (int)Math.ceil(totalCount/(double)listCnt);  
			//             `127 / 10.0  --> 12.7 -->올림 -->13.0-->13
		}
		
		
		
		//이전 화살표 표현 유무
		boolean prev = false;
		if(startPageBtnNo != 1) {
			prev = true;
		}
		
		//////////////////////////////////////////
		// 맵으로 모두 리턴하기
		//////////////////////////////////////////
		Map<String, Object> listMap = new HashMap<String, Object>();
		listMap.put("boardList", boardList);
		listMap.put("prev", prev);
		listMap.put("startPageBtnNo", startPageBtnNo);
		listMap.put("endPageBtnNo", endPageBtnNo);
		listMap.put("next", next);
		
		return listMap;
	}
	

	// 게시판 리스트
	public List<BoardVo> getList(String keyword) {
		System.out.println("[BoardService.getList()]");

		List<BoardVo> boardList = boardDao.selectList(keyword);

		return boardList;
	}

	
	// 게시글 삭제
	public int removeBoard(int no) {
		System.out.println("[BoardService.removeBoard()]");

		int count = boardDao.deleteBoard(no);

		return count;
	}

	
	// 글 가져오기(읽기)
	public BoardVo getBoardRead(int no) {
		System.out.println("[BoardService.getBoard()]");

		// 조회수 올리기
		int count = boardDao.updateHit(no);

		// 게시판 정보 가져오기
		BoardVo boardVo = boardDao.selectBoard(no);

		return boardVo;
	}

	
	// 글 가져오기(수정폼)
	public BoardVo getBoardModifyForm(int no) {
		System.out.println("[BoardService.getBoard()]");

		// 게시판 정보 가져오기
		BoardVo boardVo = boardDao.selectBoard(no);

		return boardVo;
	}

	
	// 글수정
	public int modifyBoard(BoardVo boardVo) {
		System.out.println("[BoardService.modifyBoard()]");

		return boardDao.updateBoard(boardVo);
	}

	
	// 글쓰기
	public int writeBoard(BoardVo boardVo) {
		System.out.println("[BoardService.addBoard()]");

		for(int i=0; i<127; i++) {
			boardVo.setTitle(i + "번째 제목입니다.");
			boardVo.setContent(i + "번째 내용입니다.");
			boardDao.insertBoard(boardVo);
		}
		
		return 1;
		/* return boardDao.insertBoard(boardVo); */
	}

}
