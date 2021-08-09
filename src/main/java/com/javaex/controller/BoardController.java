package com.javaex.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.javaex.service.BoardService;
import com.javaex.vo.BoardVo;
import com.javaex.vo.UserVo;

@Controller
@RequestMapping(value = "/board")
public class BoardController {

	@Autowired
	private BoardService boardService;

	//게시판 페이징 연습용 리스트
	@RequestMapping(value = "/list2", method = { RequestMethod.GET, RequestMethod.POST })
	public String list2(Model model, 
						@RequestParam(value= "crtPage", required = false, defaultValue = "1") int crtPage,
						@RequestParam(value= "keyword", required = false, defaultValue = "") String keyword) {
		System.out.println("[BoardController.list2()]");
		
		Map<String, Object> listMap =  boardService.getList2(crtPage, keyword);
		System.out.println(listMap);
		
		model.addAttribute("listMap", listMap);
		
		
		return "board/list2";
	}
	
	
	
	
	// 게시판 리스트
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public String list(Model model,
			@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword) {
		System.out.println("[BoardController.list()]");
		List<BoardVo> boardList = boardService.getList(keyword);

		model.addAttribute("boardList", boardList);

		return "board/list";
	}

	// 게시글 삭제
	@RequestMapping(value = "/board/remove", method = { RequestMethod.GET, RequestMethod.POST })
	public String remove(Model model, @RequestParam("no") int no) {
		System.out.println("[BoardController.remove()]");

		int count = boardService.removeBoard(no);

		return "redirect:/board/list";
	}

	// 게시판 읽기
	@RequestMapping(value = "/read", method = { RequestMethod.GET, RequestMethod.POST })
	public String read(Model model, @RequestParam("no") int no) {
		System.out.println("[BoardController.read()]");

		BoardVo boardVo = boardService.getBoardRead(no);

		model.addAttribute("boardVo", boardVo);

		return "board/read";
	}

	// 수정폼
	@RequestMapping(value = "/modifyForm", method = { RequestMethod.GET, RequestMethod.POST })
	public String modifyform(Model model, @RequestParam("no") int no, HttpSession session) {
		System.out.println("[BoardController.modifyForm()]");

		BoardVo boardVo = boardService.getBoardModifyForm(no);

		UserVo authUser = (UserVo) session.getAttribute("authUser");

		// 로그인 안한경우 -->메인
		if (authUser == null) {
			System.out.println("로그인 안한경우");
			return "redirect:/main";
		}

		if (authUser.getNo() == boardVo.getUserNo()) { // 로그인한 사용자 == 글작성자
			System.out.println("자신의 글인 경우-->수정폼 포워드");
			model.addAttribute("boardVo", boardVo);
			return "board/modifyForm";

		} else {
			System.out.println("다른사람 글인 경우");
			return "redirect:/board/list";
		}

	}

	// 글수정
	@RequestMapping(value = "/modify", method = { RequestMethod.GET, RequestMethod.POST })
	public String modify(Model model, @ModelAttribute BoardVo boardVo, HttpSession session) {
		System.out.println("[BoardController.modify()]");

		UserVo authUser = (UserVo) session.getAttribute("authUser");
		boardVo.setUserNo(authUser.getNo());
		boardService.modifyBoard(boardVo);

		return "redirect:/board/list";
	}

	// 글쓰기 폼
	@RequestMapping(value = "/writeForm", method = { RequestMethod.GET, RequestMethod.POST })
	public String writeForm(HttpSession session) {
		System.out.println("[BoardController.writeForm()]");

		UserVo authUser = (UserVo) session.getAttribute("authUser");
		
		// 로그인 안한경우 -->메인
		if (authUser == null) {
			System.out.println("로그인 안한경우");
			return "redirect:/user/loginForm";
		}

		return "board/writeForm";
	}

	// 글쓰기
	@RequestMapping(value = "/write", method = { RequestMethod.GET, RequestMethod.POST })
	public String write(@ModelAttribute BoardVo boardVo, HttpSession session) {
		System.out.println("[BoardController.write()]");

		UserVo authUser = (UserVo) session.getAttribute("authUser");
		boardVo.setUserNo(authUser.getNo());
		boardService.writeBoard(boardVo);

		return "redirect:/board/list";
	}

}
