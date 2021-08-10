package com.javaex.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.javaex.service.UserService;
import com.javaex.vo.UserVo;

@Controller
@RequestMapping(value = "/user")
public class UserController {

	// 필드
	@Autowired
	private UserService userService;

	// 생성자
	// 메소드gs
	// 메소드일반

	// 로그인폼
	@RequestMapping(value = "/loginForm", method = { RequestMethod.GET, RequestMethod.POST })
	public String loginForm() {
		System.out.println("[UserController.loginForm()]");

		return "user/loginForm";
	}

	// 로그인
	@RequestMapping(value = "/login", method = { RequestMethod.GET, RequestMethod.POST })
	public String login(@ModelAttribute UserVo userVo, HttpSession session) {
		System.out.println("[UserController.login()]");

		UserVo authUser = userService.getUser(userVo);

		if (authUser != null) { // 로그인 성공하면(authUser null이 아니면)
			System.out.println("[로그인성공]");
			session.setAttribute("authUser", authUser);
			return "redirect:/main";
		} else { // 로그인 성공하면(authUser null이 아니면)
			System.out.println("[로그인실패]");
			return "redirect:/user/loginForm?result=fail";
		}
	}

	// 로그아웃
	@RequestMapping(value = "/logout", method = { RequestMethod.GET, RequestMethod.POST })
	public String logout(HttpSession session) {
		System.out.println("[UserController.logout()]");

		// 세션의 값을 삭제한다.
		session.removeAttribute("authUser");
		session.invalidate();
		return "redirect:/main";
	}

	// 회원가입 폼
	@RequestMapping("/joinForm")
	public String joinForm() {
		System.out.println("[UserController.joinForm()]");

		return "user/joinForm";
	}

	// 회원가입
	@RequestMapping("/join")
	public String join(@ModelAttribute UserVo userVo) {
		System.out.println("[UserController.join()]");

		// 서비스를 통해 회원정보 저장
		int count = userService.joinUser(userVo);
		return "user/joinOk";
	}

	
	// 회원가입
	@ResponseBody
	@RequestMapping("/join2")
	public int join2(@RequestBody UserVo userVo) {
		System.out.println("[UserController.join()]");

		// 서비스를 통해 회원정보 저장
		int count = userService.joinUser(userVo);
		return count;
	}
	
	
	
	// 회원정보수정폼
	@RequestMapping("/modifyForm")
	public String modifyForm(Model model, HttpSession session) {
		System.out.println("[UserController.modifyForm()]");

		// 세션에서 로그인한 사용자 no값 가져오기
		int no = ((UserVo) session.getAttribute("authUser")).getNo();

		// userService를 통해 로그인한 유저 모든정보 가져오기
		UserVo userVo = userService.getUser(no);

		// Dispacher Servlet에 유저정보 전달
		model.addAttribute("userVo", userVo);
		return "user/modifyForm";
	}

	// 회원수정
	@RequestMapping("/modify")
	public String modify(@ModelAttribute UserVo userVo, HttpSession session) {
		System.out.println("[UserController.modify()]");

		// 세션에서 로그인한 사용자 정보 가져오기
		UserVo authUser = (UserVo) session.getAttribute("authUser");

		// 로그인한 사용자 no값 가져오기
		int no = authUser.getNo();

		// 로그인한 사용자 no값 추가
		userVo.setNo(no);

		// userService를 통해 로그인한 사용자 정보 수정
		userService.modifyUser(userVo);

		// 세션에 이름 변경
		authUser.setName(userVo.getName());

		return "redirect:/main";
	}
	
	// id체크
	@ResponseBody
	@RequestMapping(value = "/idcheck", method = { RequestMethod.GET, RequestMethod.POST })
	public boolean idCheck(@RequestParam("id") String id) {
		System.out.println("[UserController.idcheck()]");
		
		boolean state = userService.getUser(id);
		
		return state;
	}
	
}
