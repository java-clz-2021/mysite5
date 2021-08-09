package com.javaex.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.javaex.service.GalleryService;
import com.javaex.vo.GalleryVo;
import com.javaex.vo.UserVo;

@Controller
@RequestMapping(value="/gallery")
public class GalleryController {
	
	@Autowired
	private GalleryService galleryService;
	
	//이미지 올리기
	@RequestMapping("/upload")
	public String upload(@RequestParam("file") MultipartFile file, @ModelAttribute GalleryVo galleryVo, HttpSession session, Model model) {
		System.out.println("GalleryController/upload");
		
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		galleryVo.setUserNo(authUser.getNo()); //로그인한 사용자 정보
		System.out.println(galleryVo.toString());
		
		galleryService.restore(file, galleryVo); //file, galleryVo-->comment, userNo

		return "redirect:/gallery/list";
	}
	

	// 리스트(리스트 출력)

	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public String list(Model model) {
		System.out.println("GalleryController/list");

		List<GalleryVo> galleryList = galleryService.getList();
		model.addAttribute("galleryList", galleryList);
		return "gallery/list";
	}

	
	// 이미지 1개 가져오기
	@ResponseBody
	@RequestMapping(value = "/read", method = { RequestMethod.GET, RequestMethod.POST })
	public GalleryVo read(@RequestParam("no") int no) {
		System.out.println("GalleryController/read");

		GalleryVo galleryVo = galleryService.getGallery(no);
		System.out.println(galleryVo.toString());
		return galleryVo;
	}

	// 이미지 삭제하기
	@ResponseBody
	@RequestMapping(value = "/remove", method = { RequestMethod.GET, RequestMethod.POST })
	public int remove(@RequestParam("no") int no) {
		System.out.println("GalleryController/remove");
		
		return galleryService.deleteGallery(no);
	}
	
}
