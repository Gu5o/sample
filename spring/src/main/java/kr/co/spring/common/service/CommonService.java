package kr.co.spring.common.service;

import java.util.HashMap;

import kr.co.spring.file.model.FileItem;

public interface CommonService {
	
	public FileItem getFileItem(HashMap<String, Object> paramMap) throws Exception;
	
}
