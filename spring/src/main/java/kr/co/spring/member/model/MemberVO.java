package kr.co.spring.member.model;

public class MemberVO {
	private int mem_seq_no;
	private String mem_id;
	private String mem_name;
	private String mem_pwd;
	private String mem_birth;
	private String mem_phone;
	private String mem_email;
	private String mem_zipcode;
	private String mem_addr_master;
	private String mem_addr_detail;
	private String mem_type;
	public int getMem_seq_no() {
		return mem_seq_no;
	}
	
	public void setMem_seq_no(int mem_seq_no) {
		this.mem_seq_no = mem_seq_no;
	}
	public String getMem_id() {
		return mem_id;
	}
	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}
	public String getMem_name() {
		return mem_name;
	}
	public void setMem_name(String mem_name) {
		this.mem_name = mem_name;
	}
	public String getMem_pwd() {
		return mem_pwd;
	}
	public void setMem_pwd(String mem_pwd) {
		this.mem_pwd = mem_pwd;
	}
	public String getMem_birth() {
		return mem_birth;
	}
	public void setMem_birth(String mem_birth) {
		this.mem_birth = mem_birth;
	}
	public String getMem_phone() {
		return mem_phone;
	}
	public void setMem_phone(String mem_phone) {
		this.mem_phone = mem_phone;
	}
	public String getMem_email() {
		return mem_email;
	}
	public void setMem_email(String mem_email) {
		this.mem_email = mem_email;
	}
	public String getMem_zipcode() {
		return mem_zipcode;
	}
	public void setMem_zipcode(String mem_zipcode) {
		this.mem_zipcode = mem_zipcode;
	}
	public String getMem_addr_master() {
		return mem_addr_master;
	}
	public void setMem_addr_master(String mem_addr_master) {
		this.mem_addr_master = mem_addr_master;
	}
	public String getMem_addr_detail() {
		return mem_addr_detail;
	}
	public void setMem_addr_detail(String mem_addr_detail) {
		this.mem_addr_detail = mem_addr_detail;
	}
	public String getMem_type() {
		return mem_type;
	}
	public void setMem_type(String mem_type) {
		this.mem_type = mem_type;
	}

}
