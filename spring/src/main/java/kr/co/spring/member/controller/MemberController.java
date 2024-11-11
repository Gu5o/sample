package kr.co.spring.member.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import kr.co.spring.common.util.PagingUtil;
import kr.co.spring.member.model.MemberVO;
import kr.co.spring.member.service.MemberService;

@Controller
public class MemberController {

	@Autowired
	private MemberService memberService;

	@Inject
	PasswordEncoder passwordEncoder;

	@RequestMapping(value = "/member/memberTest")
	public String memberTest(Model model) throws Exception {

		ArrayList<MemberVO> memberList = memberService.selectMemberTest();
		model.addAttribute("memberList", memberList);

		return "member/memberTest";
	}

	@RequestMapping(value = "/member/memberList")
	public String memberList(@RequestParam(value = "searchType", required = false, defaultValue = "") String searchType,
			@RequestParam(value = "searchWord", required = false, defaultValue = "") String searchWord,
			@RequestParam(value = "currentPage", required = false, defaultValue = "1") int currentPage,
			@RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
			Model model

	) throws Exception {

		int pageCount = 5;
		int totalCount = 0;

		List<MemberVO> memberList = null;

		// memberList의 searchType 아이디 선택시 id , 이름 name 이라는 value 값이 들어옴

		Map<String, Object> paramMap = new HashMap<String, Object>();

		if (!StringUtils.isBlank(searchType) && !StringUtils.isBlank(searchWord)) {
			paramMap.put("searchType", searchType);
			paramMap.put("searchWord", searchWord);
		}
		// 게시물 총 개수 구하기
		totalCount = memberService.getMemberCount(paramMap);

		PagingUtil pagingUtil = new PagingUtil(currentPage, totalCount, pageSize, pageCount);

		paramMap.put("startRow", pagingUtil.getStartRow());
		paramMap.put("endRow", pagingUtil.getEndRow());

		memberList = memberService.getMemberList(paramMap);

		model.addAttribute("memberList", memberList);
		model.addAttribute("pagingUtil", pagingUtil);

		return "member/memberList";
	}

	@RequestMapping(value = "/member/memberView")
	public String memberView(@RequestParam(value = "memSeqNo", required = true) int memSeqNo, Model model)
			throws Exception {

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("memNoBer", memSeqNo);

		MemberVO memberVO = memberService.getMember(paramMap);

		model.addAttribute("member", memberVO);

		return "member/memberView"; // new String(""member/memberView"");
	}

	// 회원가입(등록), 수정

	@RequestMapping(value = "/member/memberForm")
	public ModelAndView memberForm(@RequestParam(value = "seqNoBer", required = false, defaultValue = "0") int seqNoBer)
			throws Exception {

		MemberVO member = new MemberVO();

		// 0이 아니면 수정
		if (seqNoBer != 0) {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("memNoBer", seqNoBer);

			member = memberService.getMember(paramMap);
		}

		ModelAndView mav = new ModelAndView();
		mav.addObject("member", member);
		mav.setViewName("member/memberForm");

		return mav;
	}

	@RequestMapping(value = "/member/memberExists", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> memberExists(@RequestParam(value = "mem_id", required = true) String mem_id)
			throws Exception {

		Map<String, Object> paramMap = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();

		paramMap.put("mem_id", mem_id);

		MemberVO member = memberService.getMember(paramMap);

		if (member != null) {
			resultMap.put("result", "true");
		} else {
			resultMap.put("result", "flase");
		}

		return resultMap;
	}

	@RequestMapping(value = "/member/memberInsert", method = RequestMethod.POST)
	public String memberInsert(MemberVO member, Model model) throws Exception {
		boolean isError = false;

		try {
			String endPwd = passwordEncoder.encode(member.getMem_pwd());
			member.setMem_pwd(endPwd);

			int updCnt = memberService.insertMember(member);

			if (updCnt == 0) {
				isError = true;
			}
		} catch (Exception e) {
			isError = true;
			e.printStackTrace();
		}

	
		String viewPage = "redirect:/member/memberList";

		String message = "정상적으로 회원가입 되었습니다";

		if (isError) {
			message = "회원등록에 실패했습니다.";
			viewPage = "common/message";
			model.addAttribute("isError", isError);
			model.addAttribute("message", message);
		}

		return viewPage;
	}

	@RequestMapping(value = "/member/memberUpdate")
	public String memberUpdate(MemberVO member, Model model) throws Exception {
		boolean isError = false;

		try {
			String endPwd = passwordEncoder.encode(member.getMem_pwd());
			member.setMem_pwd(endPwd);

			int updCnt = memberService.updateMember(member);

			if (updCnt == 0) {
				isError = true;
			}
		} catch (Exception e) {
			isError = true;
			e.printStackTrace();
		}

		String viewPage = "redirect:/member/memberView?memSeqNo=" + member.getMem_seq_no();

		String message = "정상적으로 수정되었습니다";

		if (isError) {
			message = "회원등록에 실패했습니다.";
			viewPage = "common/message";
			model.addAttribute("isError", isError);
			model.addAttribute("message", message);
		}

		return viewPage;
	}

	@RequestMapping(value = "/member/memberDelete")
	public String memberDelete(@RequestParam(value = "seqNoBer", required = true) int memSeqNo, MemberVO member,
			Model model) throws Exception {
		boolean isError = false;

		Map<String, Object> paramMap = new HashMap<String, Object>();

		paramMap.put("mem_seq_no", memSeqNo);
		int updCnt = memberService.deleteMember(paramMap);

		if (updCnt == 0) {
			isError = true;
		}

		String viewPage = "redirect:/member/memberList";

		String message = "정상적으로 삭제되었습니다";

		if (isError) {
			message = "회원삭제에 실패했습니다.";
			viewPage = "common/message";
			model.addAttribute("isError", isError);
			model.addAttribute("message", message);
		}

		return viewPage;

	}
}
