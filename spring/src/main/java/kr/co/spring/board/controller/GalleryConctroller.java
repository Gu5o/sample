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
@RequestMapping(value="/gallery")
public class GalleryConctroller {
	@Autowired
	BoardService boardService;
	
	@RequestMapping(value = "/galleryList")
	public String galleryList(@RequestParam(value = "searchType", required = false, defaultValue = "") String searchType,
			@RequestParam(value = "searchWord", required = false, defaultValue = "") String searchWord,
			@RequestParam(value = "currentPage", required = false, defaultValue = "1") int currentPage,
			@RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
			@RequestParam(value = "bo_type", required = false, defaultValue = "") String boType,
			Model model)
			throws Exception {

		int pageCount = 5;
		int totalCount = 0;

		List<BoardVO> galleryList = null;

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

		galleryList = boardService.selectGalleryList(paramMap);

		model.addAttribute("galleryList", galleryList);
		model.addAttribute("pagingUtil", pagingUtil);

		return "board/galleryList";
	}// end galleryList
	
	@RequestMapping(value = "/galleryForm")
	public ModelAndView galleryForm(@RequestParam(value = "boSeqNo", required = false, defaultValue = "0") int boSeqNo,
			Model model,
			HttpSession session
			) throws Exception {

		BoardVO gallery = new BoardVO();

		// 0이 아니면 데이터수정
		if (boSeqNo != 0) {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("bo_seq_no", boSeqNo);
			gallery = boardService.getBoard(paramMap); // 데이터 조회해서 화면에 보여주기\

		} else {
			
			MemberVO member = (MemberVO) session.getAttribute("LOGIN_USER");
			gallery.setBo_writer(member.getMem_id());
			gallery.setBo_writer_name(member.getMem_name());
			
		}
		ModelAndView mav = new ModelAndView();
		mav.addObject("gallery", gallery);
		mav.setViewName("board/galleryForm");

		return mav;

	}// end galleryForm
	
	@RequestMapping(value="/galleryInsert" , method = RequestMethod.POST)
	public String galleryInsert(
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
		
		String viewPage = "redirect:/gallery/galleryList?bo_type=GALLERY";
		String message = "글이 등록되었습니다.";
		
		if(isError) {
			message = "글 동록에 실패했습니다.";
			viewPage = "common/message";
			model.addAttribute("isError", isError);
			model.addAttribute("message", message);	
		}
		return viewPage;
	   
	}// end galleryInsert
	
	@RequestMapping(value = "/galleryUpdate" , method= RequestMethod.POST)
	public String galleryUpdate(
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
		String viewPage = "redirect:/gallery/galleryView?boSeqNo="+board.getBo_seq_no();
		String message = "글이 수정되었습니다.";
		
		if(isError) {
			message = "글 수정에 실패했습니다.";
			viewPage = "common/message";
			model.addAttribute("isError", isError);
			model.addAttribute("message", message);	
		}
		return viewPage;
	}// end galleryUpdate
	
	@RequestMapping(value = "/galleryDelete", method = {RequestMethod.POST, RequestMethod.GET})
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
		String viewPage = "redirect:/gallery/galleryList?bo_type=GALLERY";
		String message = "글이 수정되었습니다.";
		
		if(isError) {
			message = "글 수정에 실패했습니다.";
			viewPage = "common/message";
			model.addAttribute("isError", isError);
			model.addAttribute("message", message);	
		}
		return viewPage;
	}//end gallerydelet
	
	@RequestMapping(value = "/galleryView")
	public String galleryView(@RequestParam(value = "boSeqNo", required = true, defaultValue = "0") int boSeqNo,
			Model model) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();

		paramMap.put("bo_seq_no", boSeqNo);

		BoardVO gallery = boardService.getBoard(paramMap);

		model.addAttribute("gallery", gallery);

		return "board/galleryView";
	}// end boardView
}
