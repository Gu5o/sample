package kr.co.spring.board.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import kr.co.spring.board.model.BoardVO;

public interface  BoardDAO {
	//전체 개수 조회
	public int selectBoardCount(Map<String, Object> paramMap) throws Exception;
	
	//목록 조회
	public List<BoardVO> selectBoardList(Map<String, Object> paramMap) throws Exception;
	
	//상세조회 1건
	public BoardVO getBoard(Map<String, Object> paramMap) throws Exception;
	
	//게시글 조회 수 업데이트
	public int updateHitCnt(int boSeqNo) throws Exception; 
	
	//게시글 생성
	public int insertBoard(BoardVO board) throws Exception;
	
	public int updateBoard(BoardVO board) throws Exception;
	
	public int deleteBoard(BoardVO board) throws Exception;
	
	public ArrayList<BoardVO> selectGalleryList(Map<String, Object> paramMap) throws Exception;
}
