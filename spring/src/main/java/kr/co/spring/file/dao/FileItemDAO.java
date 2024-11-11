package kr.co.spring.file.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import kr.co.spring.file.model.FileItem;

public interface FileItemDAO {

	public ArrayList<FileItem> selectFileItemList(HashMap<String, Object> paramMap) throws Exception;

	public FileItem selectFileItem(Map<String, Object> paramMap) throws Exception;

	public int insertFileItem(FileItem fileItem) throws Exception;

	public int deleteFileItemList(Map<String, Object> paramMap) throws Exception;
}
