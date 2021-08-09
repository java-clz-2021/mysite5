package com.javaex.service;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.javaex.dao.GalleryDao;
import com.javaex.vo.GalleryVo;


@Service
public class GalleryService {

	@Autowired
	private GalleryDao galleryDao;
	
	public String restore(MultipartFile file, GalleryVo galleryVo) {
		System.out.println("GalleryService/restore");
		
		String saveDir = "C:\\javaStudy\\upload"; 
		
		//-원파일이름
		String orgName = file.getOriginalFilename();
		System.out.println("orgName:" + orgName);
		
		//확장자
		String exName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		System.out.println("exName:" + exName);
		
		//-저장파일이름
		String saveName = System.currentTimeMillis() + UUID.randomUUID().toString() + exName;
		System.out.println("saveName:" + saveName);
		
		//-파일패스 생성(
		String filePath = saveDir + "\\" + saveName;
		System.out.println("filePath:" + filePath);
		
		//파일 사이즈
		long fileSize = file.getSize();
		System.out.println("fileSize:" + fileSize);
		
		
		//파일업로드(복사)
		try {
			byte[] fileData = file.getBytes();
			OutputStream out = new FileOutputStream( filePath );
			BufferedOutputStream bout = new BufferedOutputStream(out);
			
			bout.write( fileData );
			
			if (bout != null) {
				bout.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//파일정보 정리
		galleryVo.setFilePath(filePath);
		galleryVo.setOrgName(orgName);
		galleryVo.setSaveName(saveName);
		galleryVo.setFileSize(fileSize);
		
		System.out.println("=======");
		System.out.println(galleryVo.toString());
		
		//파일정보 DB저장
		galleryDao.insert(galleryVo);
		
		return saveName;
	}
	
	
	//전체리스트
	public List<GalleryVo> getList() {
		System.out.println("GalleryService/getList");
		return galleryDao.selectList();
	}
	
	//이미지1개 가져오기
	public GalleryVo getGallery(int no) {
		System.out.println("GalleryService/getGallery");
		return galleryDao.selectGallery(no);
	}
	
	//삭제하기
	public int deleteGallery(int no) {
		System.out.println("GalleryService/deleteGallery");
		return galleryDao.delete(no);
	}
		
}
