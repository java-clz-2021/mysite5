package com.javaex.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaex.dao.UserDao;
import com.javaex.vo.UserVo;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;

	// 로그인(사용자정보 가져오기)
	public UserVo getUser(UserVo userVo) {
		System.out.println("[UserService.getUser()]");

		UserVo authUser = userDao.selectUser(userVo);

		return authUser;

	}

	// 회원가입
	public int joinUser(UserVo userVo) {
		System.out.println("[UserService.joinUser()]");

		int count = userDao.insertUser(userVo);
		return count;
	}

	// 회원정보 수정폼
	public UserVo getUser(int no) {
		System.out.println("[UserService.getUser()]");

		UserVo userVo = userDao.selectUser(no);
		return userVo;
	}
	
	
	//회원정보 수정
	public int modifyUser(UserVo userVo) {
		System.out.println("[UserService.modifyUser()]");
		
		int count = userDao.updateUser(userVo);
		return count;
	}

}
