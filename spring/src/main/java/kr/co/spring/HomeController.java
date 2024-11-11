package kr.co.spring;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.sql.SQLException;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

	@Autowired
	BasicDataSource dataSource;

   @RequestMapping(value = "/") // method = RequestMethod.GET
   public String home(Locale locale, Model model){
   
      Date date = new Date();
      DateFormat format = 
            DateFormat.getDateTimeInstance(DateFormat.LONG,
                  DateFormat.LONG, locale);
      String formatDate = format.format(date);
      
      model.addAttribute("serverTime", formatDate);
      
      return "home"; 
   }
   
   @RequestMapping(value = "/dbTest")
   public String dbTest(Locale locale, Model model) {
      Connection conn = null;
      Statement stmt = null;
      ResultSet rs = null;
      
      try {
         conn = dataSource.getConnection();
         
         stmt = conn.createStatement();
         rs = stmt.executeQuery("select mem_email from tb_member where mem_name='test9'");
         
         while(rs.next()) {
            model.addAttribute("mem_email",rs.getString("mem_email"));
         }
         
      }catch(SQLException e) {
         
         e.printStackTrace();
      }finally {
      if(conn != null)
         try {
        	conn.close();
            if(stmt !=null) stmt.close();
            if(rs != null) rs.close();
         }catch(SQLException e) {
            e.printStackTrace();
         }
      }
      return "dbTest";
      
   }
   

   
}
