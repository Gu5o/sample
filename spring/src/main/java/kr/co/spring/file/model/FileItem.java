package kr.co.spring.file.model;

public class FileItem {
	   private int file_seq_no;
	   private int ref_seq_no;
	   private String biz_type;
	   private String file_path;
	   private String file_name;
	   private String file_save_name;
	   private long file_size;
	   private String file_fancy_size;
	   private int file_down_cnt;
	   private String reg_date;
	   private String reg_user;
	   private String upd_date;
	   private String upd_user;
	   
	   private String thum_save_name;
	   
	  //Lombok이라는 라이브러리를 사용하면 getter, setter를 만들지 않아도 @getter, @setter 어노테이션을 이용
	  //시스템 내부에 자동으로 메서드가 생성됨
	public int getFile_seq_no() {
		return file_seq_no;
	}
	public void setFile_seq_no(int file_seq_no) {
		this.file_seq_no = file_seq_no;
	}
	public int getRef_seq_no() {
		return ref_seq_no;
	}
	public void setRef_seq_no(int ref_seq_no) {
		this.ref_seq_no = ref_seq_no;
	}
	public String getBiz_type() {
		return biz_type;
	}
	public void setBiz_type(String biz_type) {
		this.biz_type = biz_type;
	}
	public String getFile_path() {
		return file_path;
	}
	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	public String getFile_save_name() {
		return file_save_name;
	}
	public void setFile_save_name(String file_save_name) {
		this.file_save_name = file_save_name;
	}
	public long getFile_size() {
		return file_size;
	}
	public void setFile_size(long file_size) {
		this.file_size = file_size;
	}
	public String getFile_fancy_size() {
		return file_fancy_size;
	}
	public void setFile_fancy_size(String file_fancy_size) {
		this.file_fancy_size = file_fancy_size;
	}
	public int getFile_down_cnt() {
		return file_down_cnt;
	}
	public void setFile_down_cnt(int file_down_cnt) {
		this.file_down_cnt = file_down_cnt;
	}
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	public String getReg_user() {
		return reg_user;
	}
	public void setReg_user(String reg_user) {
		this.reg_user = reg_user;
	}
	public String getUpd_date() {
		return upd_date;
	}
	public void setUpd_date(String upd_date) {
		this.upd_date = upd_date;
	}
	public String getUpd_user() {
		return upd_user;
	}
	public void setUpd_user(String upd_user) {
		this.upd_user = upd_user;
	}
	public String getThum_save_name() {
		return thum_save_name;
	}
	public void setThum_save_name(String thum_save_name) {
		this.thum_save_name = thum_save_name;
	}
	   
	   
}
