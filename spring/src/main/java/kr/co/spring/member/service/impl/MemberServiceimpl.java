package kr.co.spring.member.service.impl;

import kr.co.spring.member.dao.MemberDAO;
import kr.co.spring.member.model.MemberVO;
import kr.co.spring.member.service.MemberService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceimpl implements MemberService {

	@Autowired
	MemberDAO memberDao;

	@Override
	public ArrayList<MemberVO> selectMemberTest() throws Exception {
		return memberDao.selectMemberTest();
	}

	@Override
	public int getMemberCount(Map<String, Object> paramMap) throws Exception {
		return memberDao.selectMemberCount(paramMap);
	}

	@Override
	public List<MemberVO> getMemberList(Map<String, Object> paramMap) throws Exception {
		return memberDao.selectMemberList(paramMap);
	}

	@Override
	public MemberVO getMember(Map<String, Object> paramMap) throws Exception {

		return memberDao.selectMember(paramMap);
	}

	@Override
	public int insertMember(MemberVO member) throws Exception {
		
		return memberDao.insertMember(member);
	}

	@Override
	public int updateMember(MemberVO member) throws Exception {
		
		return memberDao.updateMember(member);
	}
	
	@Override
	public int deleteMember(Map<String, Object> paramMap) throws Exception {
		
		return memberDao.deleteMember(paramMap);
	}
}
