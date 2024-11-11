package kr.co.spring.common.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.co.spring.board.model.BoardVO;
import kr.co.spring.file.model.FileItem;

//@Component 어노테이션은 개발자가 직접 만든 클래스를 bean으로 등록하기 위한 어노테이션
@Component("fileUtils")
public class FileUtils {
	public static final String FILE_PATH = "D:\\SpringUpload\\upload"; // filePath -카멜표기법

	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

	public List<FileItem> uploadFiles(BoardVO board, MultipartHttpServletRequest mRequest) throws Exception {

		List<FileItem> fileList = new ArrayList<FileItem>(); // 테이블에 저장하기 위한 목록
		// 실제 파일 목록(boardFoem.jsp의 uploadFiles라는 name을 찾아 파일을 가져옴)
		List<MultipartFile> mfList = mRequest.getFiles("uploadFiles");

		File file = null;
		// 파일 목록에서 파일 한 건별 데이터를 생성하기 위한 부분
		for (MultipartFile parts : mfList) {
			if (parts.isEmpty() == false) { // 저장할 파일이 있는지(수정할 때는 새로 추가된 파일이 있는지)
				// FileItem정보 생성(tb_file_item 테이블에 저장할 내용)
				FileItem fileItem = new FileItem();
				fileItem.setRef_seq_no(board.getBo_seq_no());
				fileItem.setReg_user(board.getBo_writer());
				fileItem.setBiz_type(board.getBo_type());
				fileItem.setFile_size(parts.getSize());
				fileItem.setFile_fancy_size(getFancySize(parts.getSize()));
				fileItem.setFile_name(parts.getOriginalFilename());
				fileItem.setFile_save_name(getSaveName() + "_" + parts.getOriginalFilename());
				fileItem.setFile_path(board.getBo_type() + "/" + dateFormat.format(new Date()));

				try {
					file = new File(FILE_PATH + "/" + fileItem.getFile_path() + "/" + fileItem.getFile_save_name());
					if (file.exists() == false) {
						file.mkdirs();
					}

					parts.transferTo(file);
					
					//확장자 추출 파일명의 뒤에서부터 문자열을 추출, test.test.txt
					String ext = parts.getOriginalFilename().substring(
							parts.getOriginalFilename().lastIndexOf(".") + 1);

					//확장자에 따라 이미지 파일이면 섬네일을 생성
					if(MediaUtils.getMediaType(ext) != null && ("GALLERY").equals(board.getBo_type())) {
						String thumSaveName = createThumnail(
								fileItem.getFile_path(), fileItem.getFile_save_name(), ext);
						
						fileItem.setThum_save_name(thumSaveName);//fileItem에 썸네일용 이미지명 저장
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				fileList.add(fileItem);
			}
		}
		return fileList;
	}







	public static String getSaveName() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	public static String getFancySize(long size) {
		String fancy = "";
		DecimalFormat decimalFormat = new DecimalFormat();

		if (size < 1024) {
			fancy = decimalFormat.format(size) + "bytes";
		} else if (size < (1024 * 1024)) {
			fancy = decimalFormat.format(size / 1024.0) + "KB";
		} else {
			fancy = decimalFormat.format(size / (1024.0 * 1024.0)) + "MB";
		}
		return fancy;
	}
	
	private String createThumnail(
			String path, String fileName, String ext) {
		BufferedImage sourceImg;
		String thumbnailName = "";
		
		try {
			//업로드 된 이미지
			sourceImg = ImageIO.read(new File(FILE_PATH + File.separator + path, fileName));
			
			//리사이즈할 이미지, 썸네일 너비, 썸네일 높이, 너비나 높이 기준 px
			BufferedImage destImg = Scalr.resize(sourceImg, Scalr.Method.AUTOMATIC,
					Scalr.Mode.FIT_TO_WIDTH, 100);
			
			//      \ /
			thumbnailName = FILE_PATH + File.separator + path + File.separator + "thumb_" + fileName;
			
			//썸네일 이미지 파일 객체 생성
			File newFile = new File(thumbnailName);
			
			//저장할 이미지, 저장할 이름, 경로
			ImageIO.write(destImg, ext, newFile);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "thumb_" + fileName;
	}



}
