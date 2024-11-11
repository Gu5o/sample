package kr.co.spring.board.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import kr.co.spring.board.model.BoardVO;
import kr.co.spring.board.service.BoardService;
import kr.co.spring.common.util.PagingUtil;
import kr.co.spring.member.model.MemberVO;

@Controller
@RequestMapping(value = "/board")
public class BoardController {

	@Autowired
	BoardService boardService;

	@RequestMapping(value = "/boardList")
	public String boardList(@RequestParam(value = "searchType", required = false, defaultValue = "") String searchType,
			@RequestParam(value = "searchWord", required = false, defaultValue = "") String searchWord,
			@RequestParam(value = "currentPage", required = false, defaultValue = "1") int currentPage,
			@RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
			@RequestParam(value = "bo_type", required = false, defaultValue = "") String boType,
			Model model)
			throws Exception {

		int pageCount = 5;
		int totalCount = 0;

		List<BoardVO> boardList = null;

		Map<String, Object> paramMap = new HashMap<String, Object>();

		if (!StringUtils.isBlank(searchType) && !StringUtils.isBlank(searchWord)) {
			paramMap.put("searchType", searchType);
			paramMap.put("searchWord", searchWord);
		}
		paramMap.put("bo_type", boType);
		
		totalCount = boardService.selectBoardCount(paramMap);

		PagingUtil pagingUtil = new PagingUtil(currentPage, totalCount, pageSize, pageCount);

		paramMap.put("startRow", pagingUtil.getStartRow());
		paramMap.put("endRow", pagingUtil.getEndRow());

		boardList = boardService.selectBoardList(paramMap);

		model.addAttribute("boardList", boardList);
		model.addAttribute("pagingUtil", pagingUtil);

		return "board/boardList";
	}// end boardList

	@RequestMapping(value = "/boardView")
	public String boardView(@RequestParam(value = "boSeqNo", required = true, defaultValue = "0") int boSeqNo,
			Model model) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();

		paramMap.put("bo_seq_no", boSeqNo);

		BoardVO boardVo = boardService.getBoard(paramMap);

		model.addAttribute("board", boardVo);

		return "board/boardView";
	}// end boardView

	@RequestMapping(value = "/boardForm")
	public ModelAndView boardForm(@RequestParam(value = "boSeqNo", required = false, defaultValue = "0") int boSeqNo,
			Model model,
			HttpSession session
			) throws Exception {

		BoardVO boardVO = new BoardVO();

		// 0이 아니면 데이터수정
		if (boSeqNo != 0) {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("bo_seq_no", boSeqNo);
			boardVO = boardService.getBoard(paramMap); // 데이터 조회해서 화면에 보여주기\

		} else {
			
			MemberVO member = (MemberVO) session.getAttribute("LOGIN_USER");
			boardVO.setBo_writer(member.getMem_id());
			boardVO.setBo_writer_name(member.getMem_name());
			
		}
		ModelAndView mav = new ModelAndView();
		mav.addObject("board", boardVO);
		mav.setViewName("board/boardForm");

		return mav;

	}// end boardForm

@RequestMapping(value="/boardInsert" , method = RequestMethod.POST)
public String boardInsert(
			BoardVO boardVO , Model model,
			MultipartHttpServletRequest mRequest
			) throws Exception {
	
	boolean isError = false;
	
	try {
		int updCnt = boardService.insertBoard(boardVO, mRequest);
		
		if(updCnt ==0) {
			isError = true;
		}
	}catch(Exception e) {
		isError = true;
		e.printStackTrace();
	}
	
	String viewPage = "redirect:/board/boardList?bo_type=BBS";
	String message = "글이 등록되었습니다.";
	
	if(isError) {
		message = "글 동록에 실패했습니다.";
		viewPage = "common/message";
		model.addAttribute("isError", isError);
		model.addAttribute("message", message);	
	}
	return viewPage;
   
}// end boardForm

	@RequestMapping(value = "/boardUpdate" , method= RequestMethod.POST)
	public String boardUpdate(
			BoardVO board, Model model , HttpSession session,
			MultipartHttpServletRequest mRequest
			) throws Exception{
		
		boolean isError = false;
		
		try {
			MemberVO member = (MemberVO) session.getAttribute("LOGIN_USER");
			board.setUpd_user(member.getMem_id());
			
			int updCnt = boardService.updateBoard(board, mRequest);
			
			if(updCnt ==0) {
				isError = true;
			}
		}catch(Exception e) {
			isError = true;
			e.printStackTrace();
		}
		String viewPage = "redirect:/board/boardView?boSeqNo="+board.getBo_seq_no();
		String message = "글이 수정되었습니다.";
		
		if(isError) {
			message = "글 수정에 실패했습니다.";
			viewPage = "common/message";
			model.addAttribute("isError", isError);
			model.addAttribute("message", message);	
		}
		return viewPage;
	}
	@RequestMapping(value = "/boardDelete", method = {RequestMethod.POST, RequestMethod.GET})
	public String boardDelete(
			@RequestParam(value="boSeqNo" ,required=true, defaultValue="0") int boSeqNo,
			BoardVO board, Model model , HttpSession session
			) throws Exception{
		
		boolean isError = false;
		
		try {
			MemberVO member = (MemberVO) session.getAttribute("LOGIN_USER");
			board.setUpd_user(member.getMem_id());
			board.setBo_seq_no(boSeqNo);
			int updCnt = boardService.deleteBoard(board);
			
			if(updCnt ==0) {
				isError = true;
			}
		}catch(Exception e) {
			isError = true;
			e.printStackTrace();
		}
		String viewPage = "redirect:/board/boardList?bo_type=BBS";
		String message = "글이 수정되었습니다.";
		
		if(isError) {
			message = "글 수정에 실패했습니다.";
			viewPage = "common/message";
			model.addAttribute("isError", isError);
			model.addAttribute("message", message);	
		}
		return viewPage;
	}
}
