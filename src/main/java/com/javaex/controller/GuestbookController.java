package com.javaex.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.javaex.service.GuestbookService;
import com.javaex.vo.GuestbookVo;

@Controller
@RequestMapping(value = "/guestbook")
public class GuestbookController {

	@Autowired
	private GuestbookService guestbookService;

	// 방명록 리스트 가져오기
	@RequestMapping(value = "/addList", method = {RequestMethod.GET, RequestMethod.POST})
	public String list(Model model) {
		System.out.println("[guestbookController.list()]");

		// 서비스를 통해 모든 방명록 글 가져오기
		List<GuestbookVo> guestbookList = guestbookService.getGuestList();

		// Dispacher Servlet-->jsp에 방명록 글 리스트 전달
		model.addAttribute("guestbookList", guestbookList);

		return "guestbook/addList";
	}

	// 방명록 글 저장
	@RequestMapping(value = "/write", method = {RequestMethod.GET, RequestMethod.POST})
	public String write(@ModelAttribute GuestbookVo guestbookVo) {
		System.out.println("[guestbookController.write()]");

		guestbookService.writeGuest(guestbookVo);
		return "redirect:/guestbook/addList";
	}

	// 방명록 삭제 폼
	@RequestMapping(value = "/deleteForm", method = {RequestMethod.GET, RequestMethod.POST})
	public String deleteform() {
		System.out.println("[guestbookController.deleteform()]");

		return "guestbook/deleteForm";
	}

	// 방명록 삭제
	@RequestMapping(value = "/delete", method = {RequestMethod.GET, RequestMethod.POST})
	public String delete(@ModelAttribute GuestbookVo guestbookVo) {
		System.out.println("[guestbookController.delete()]");

		guestbookService.removeGuest(guestbookVo);
		return "redirect:/guestbook/addList";
	}
	
	
	
	
	
	// ajax 방명록 메인페이지
	@RequestMapping(value = "/ajaxMain", method = {RequestMethod.GET, RequestMethod.POST})
	public String ajaxMain() {
		System.out.println("[guestbookController.ajaxMain()]");
		
		
		return "guestbook/ajaxList";
	}
	
	
	
	
	

}
