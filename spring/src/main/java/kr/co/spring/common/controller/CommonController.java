package kr.co.spring.common.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import kr.co.spring.common.service.CommonService;
import kr.co.spring.common.util.FileUtils;
import kr.co.spring.file.model.FileItem;

@Controller
public class CommonController {
	@Autowired
	CommonService commonService;
	
	@RequestMapping(value="/common/download")
	public void fileDownload(
			@RequestParam(value="file_seq_no" , required=true) String fileSeqNo
			,HttpServletResponse response) throws Exception {
		
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("file_seq_no", fileSeqNo);
		
		FileItem fileItem = commonService.getFileItem(paramMap);
		
		if(fileItem == null) {
			 throw new RuntimeException("해당 첨부파일이 존재하지 않습니다.");
		}
		
		//파일을 저장했던 위치에서 첨부파일을 읽어와 byte배열 형식으로 전환
		byte fileByte[] = org.apache.commons.io.FileUtils.readFileToByteArray(
				new File(FileUtils.FILE_PATH + "/" + fileItem.getFile_path() + "/" + 
						fileItem.getFile_save_name())); //실제로 파일이 있는 경로(업로드 파일 위치)
		
		//mime type지정, 사용자에세 어떤 파일을 제공하는 mime type을 지정
		response.setContentType("application/octet-stream"); //octet-stream 은 모든 파일을 지정
		response.setContentLength(fileByte.length); //파일의 길이
		response.setHeader("Content-Disposition",
				"attachment; fileName=\"" + URLEncoder.encode(fileItem.getFile_name(),
						"utf-8")); //attachment는 파일을 다운로드, 파일명, 인코딩설정
		response.getOutputStream().write(fileByte); //파일을 다운로드 및 저장
		response.getOutputStream().flush(); //버퍼를 비움
		response.getOutputStream().close(); //outPutStream close()
		
	}
	
	@RequestMapping(value="/common/display")
	@ResponseBody
	public ResponseEntity<byte[]> display(
				@RequestParam(value="file_seq_no", required=false) int fileSeqNo,
				@RequestParam(value="imgType", required=false) String imgType
			) throws Exception {
		
		InputStream input = null;
		ResponseEntity<byte[]> entity = null;
		
		try {
			//이미지의 물리적인 로컬 주소를 입력하는 건 크롬 보안 정책상 권장사항이 아니고
			//지원이 되지 않을 수 있어 컨트롤러 이용
			
			HashMap<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("file_seq_no", fileSeqNo);
			FileItem fileItem = commonService.getFileItem(paramMap);
			
			//확장자 추출
			//확장자 추출. 파일명의 뒤에서부터 문자열을 추출 test.test.txt
			String formatName = fileItem.getFile_name().substring(
					fileItem.getFile_name().lastIndexOf(".") + 1);
			
			MediaType mType = null;
			if(formatName.equalsIgnoreCase("jpeg") || formatName.equalsIgnoreCase("jpg")){
				mType = MediaType.IMAGE_JPEG;	
			}else if(formatName.equalsIgnoreCase("png")) {
				mType = MediaType.IMAGE_PNG;
			}else if(formatName.equalsIgnoreCase("gif")){
				mType = MediaType.IMAGE_GIF;
			}
			
			HttpHeaders header = new HttpHeaders();
			
			if(mType != null) {
				if(!StringUtils.isEmpty(imgType) && imgType.equals("img")) {
					input = new FileInputStream(FileUtils.FILE_PATH + "/" +
							fileItem.getFile_path() + "/" +
							fileItem.getFile_save_name()); 
					}else {
						input = new FileInputStream(FileUtils.FILE_PATH + "/" + 
								fileItem.getFile_path() + "/" +
								fileItem.getThum_save_name());
						
						
					}
					header.setContentType(mType);
			}
		
			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(input), header
					, HttpStatus.CREATED);
			
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(input != null) input.close();
		}
		
		return entity;
	}
}
