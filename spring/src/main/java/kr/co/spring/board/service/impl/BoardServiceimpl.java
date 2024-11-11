package kr.co.spring.board.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.co.spring.board.dao.BoardDAO;
import kr.co.spring.board.model.BoardVO;
import kr.co.spring.board.service.BoardService;
import kr.co.spring.common.util.FileUtils;
import kr.co.spring.file.dao.FileItemDAO;
import kr.co.spring.file.model.FileItem;

@Service
public class BoardServiceimpl implements BoardService {

	@Autowired
	BoardDAO boardDAO;
	
	@Autowired
	FileItemDAO fileItemDAO;
	
	@Autowired
	FileUtils fileUtils;

	@Override
	public int selectBoardCount(Map<String, Object> paramMap)  throws Exception {
		return boardDAO.selectBoardCount(paramMap);
	}

	@Override
	public List<BoardVO> selectBoardList(Map<String, Object> paramMap) throws Exception{
		return boardDAO.selectBoardList(paramMap);
	}

	@Transactional(isolation = Isolation.READ_COMMITTED)
	@Override
	public BoardVO getBoard(Map<String, Object> paramMap) throws Exception {
		int boSeqNo = (int)paramMap.get("bo_seq_no");
		boardDAO.updateHitCnt(boSeqNo);
		
		BoardVO board = boardDAO.getBoard(paramMap);
		
		HashMap<String, Object> fileMap = new HashMap<String, Object>();
		fileMap.put("ref_seq_no", board.getBo_seq_no());
		fileMap.put("biz_type", board.getBo_type());
		
		ArrayList<FileItem> fileList = fileItemDAO.selectFileItemList(fileMap);
		board.setFileList(fileList);
		
		return board;
	}
	
	@Transactional
	@Override
	public int insertBoard(BoardVO boardVo, MultipartHttpServletRequest mRequest) throws Exception {
		int updCnt = boardDAO.insertBoard(boardVo); //시퀀스 번호가 bo_seq_no에 저장되어
		
		List<FileItem> fileList = fileUtils.uploadFiles(boardVo, mRequest);
		
		for(FileItem fileItem : fileList) {
			fileItemDAO.insertFileItem(fileItem);
		}
		return updCnt;
	}

	@Transactional
	@Override
	public int updateBoard(BoardVO board, MultipartHttpServletRequest mRequest) throws Exception {
		//기존에 있는 파일을 삭제
		String[] delFileSeq = board.getDelFileSeq();
		try {
			if(delFileSeq != null) {
				for(int i = 0; i < delFileSeq.length; i++) {
					Map<String, Object> paramMap = new HashMap<String, Object>();
					paramMap.put("delFileSeq", delFileSeq[i]);
					
					fileItemDAO.deleteFileItemList(paramMap);
				}
			}
			
			//새로 추가된 파일이 있으면 업로드
			List<FileItem> fileList = fileUtils.uploadFiles(board, mRequest);
			
			for(FileItem fileItem : fileList) {
				fileItemDAO.insertFileItem(fileItem);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return boardDAO.updateBoard(board);
	}

	@Override
	public int deleteBoard(BoardVO boardVo) throws Exception {
		//게시글 삭제 시 첨부파일도 삭제하고 싶은 경우
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("refFileSeq", boardVo.getBo_seq_no());
		
		fileItemDAO.deleteFileItemList(paramMap);
		return boardDAO.deleteBoard(boardVo);
	}

	@Override
	public ArrayList<BoardVO> selectGalleryList(Map<String, Object> paramMap) throws Exception {
		
		return boardDAO.selectGalleryList(paramMap);
	}
	
	


}
