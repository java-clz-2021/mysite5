package com.javaex.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.UserVo;

@Repository
public class UserDao {

	// 필드
	@Autowired
	private SqlSession sqlSession;

	// 생성자
	// 메소드gs
	// 메소드일반
	public UserVo selectUser(UserVo userVo) {
		System.out.println("[UserDao.selectUser()]");

		return sqlSession.selectOne("user.selectUser", userVo);
	}

	// 회원정보 저장
	public int insertUser(UserVo userVo) {
		System.out.println("[UserDao.insertUser()]");

		return sqlSession.insert("user.insertUser", userVo);
	}

	// 회원정보 가져오기 ->회원정보 수정폼
	public UserVo selectUser(int no) {
		System.out.println("[UserDao.selectUser()]");

		return sqlSession.selectOne("user.selectUserByNo", no);
	}

	// 회원정보 수정
	public int updateUser(UserVo userVo) {
		System.out.println("[UserDao.updateUser()]");

		return sqlSession.update("user.updateUser", userVo);
	}

	// 회원정보 가져오기 ->회원정보 수정폼
	public UserVo selectUser(String id) {
		System.out.println("[UserDao.selectUser()]");

		return sqlSession.selectOne("user.selectUserById", id);
	}
	
}
