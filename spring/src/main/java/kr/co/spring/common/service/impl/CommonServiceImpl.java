package kr.co.spring.common.service.impl;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.spring.common.service.CommonService;
import kr.co.spring.file.dao.FileItemDAO;
import kr.co.spring.file.model.FileItem;

@Service
public class CommonServiceImpl implements CommonService{
	@Autowired
	FileItemDAO fileItemDAO;

	@Override
	public FileItem getFileItem(HashMap<String, Object> paramMap) throws Exception {
		
		return fileItemDAO.selectFileItem(paramMap);
	}
}
