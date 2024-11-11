package kr.co.spring.member.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kr.co.spring.member.model.MemberVO;


//mapper 이랑 매핑시키기 위한 인터페이스
public interface MemberDAO {
   
   public ArrayList<MemberVO> selectMemberTest() throws Exception;
   
   public int selectMemberCount() throws Exception;
   

   public List<MemberVO> selectMemberList(Map<String, Object> paramMap) throws Exception;
   
   // 회원 전체 카운트
   public int selectMemberCount(Map<String, Object> paramMap) throws Exception;
   
   // 회원정보 상세 조회
   public MemberVO selectMember(Map<String, Object> paramMap) throws Exception;
   
   // 회원강비(회원정보추가)
   public int insertMember(MemberVO member) throws Exception;
   
  
   public int updateMember(MemberVO member) throws Exception;
   
   public int deleteMember(Map<String, Object> paramMap) throws Exception;
   
}