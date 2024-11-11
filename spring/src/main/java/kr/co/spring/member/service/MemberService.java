package kr.co.spring.member.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kr.co.spring.member.model.MemberVO;

public interface MemberService {

   public ArrayList<MemberVO> selectMemberTest() throws Exception;
   
   public List<MemberVO> getMemberList(Map<String, Object> paramMap) throws Exception;

   int getMemberCount(Map<String, Object> paramMap) throws Exception;
 
 	public MemberVO getMember(Map<String, Object> paramMap) throws Exception;
   
 	public int insertMember(MemberVO member) throws Exception;
 	
 	public int updateMember(MemberVO member) throws Exception;
 	
 	public int deleteMember(Map<String, Object> paramMap) throws Exception;
}