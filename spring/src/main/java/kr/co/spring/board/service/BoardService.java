package kr.co.spring.board.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.co.spring.board.model.BoardVO;

public interface BoardService {
	public int selectBoardCount(Map<String, Object> paramMap) throws Exception;
	
	public List<BoardVO> selectBoardList(Map<String, Object> paramMap) throws Exception;
	
	public BoardVO getBoard(Map<String, Object> paramMap) throws Exception;
	
	public int insertBoard(BoardVO boardVo, MultipartHttpServletRequest mRequest) throws Exception;
	
	public int updateBoard(BoardVO boardVo, MultipartHttpServletRequest mRequest) throws Exception;
	
	public int deleteBoard(BoardVO board) throws Exception;
	
	public ArrayList<BoardVO> selectGalleryList(Map<String, Object> paramMap) throws Exception;

	
}
