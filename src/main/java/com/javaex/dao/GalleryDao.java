package com.javaex.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.GalleryVo;


@Repository
public class GalleryDao {

	@Autowired
	private SqlSession sqlSession;
	
	//갤러리 저장하기
	public int insert(GalleryVo galleryVo){
		System.out.println("GalleryDao/insert");
		System.out.println(galleryVo);
		return sqlSession.insert("gallery.insert", galleryVo);	
	}
		
	//갤러리 리스트
	public List<GalleryVo> selectList(){
		System.out.println("GalleryDao/selectList");
		
		return sqlSession.selectList("gallery.selectList");	
	}
	
	//이미지1개 가져오기
	public GalleryVo selectGallery(int no) {
		System.out.println("GalleryService/selectGallery");
		
		return sqlSession.selectOne("gallery.select", no);
	}
	
	//갤러리 삭제하기
	public int delete(int no) {
		System.out.println("GalleryService/deleteGallery");
		
		return sqlSession.delete("gallery.delete", no);
	}
			
}
