// Import required java libraries
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;



import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

// Extend HttpServlet class

  
@WebServlet("/People_details")
public class people_details extends HttpServlet {
	  
	 public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			// int customer_id  = Integer.parseInt(request.getParameter("customer_id"));
			 String url = "jdbc:oracle:thin:testuser/password@localhost"; 
		      //properties for creating connection to Oracle database
		      Properties props = new Properties();
		      
		      props.setProperty("user", "testdb");
		      props.setProperty("password", "password");
		    
		      //creating connection to Oracle database using JDBC
		      Connection conn;
			
			  PreparedStatement preStatement;
			  String  people_details = "";
			  String sql_command = "select  first_name, last_name, street_address, city, state, zip_code,position,company, email_address from customers where ID = " + request.getParameter("customer_ID");
			  try{
					conn = DriverManager.getConnection(url,props);
					preStatement = conn.prepareStatement(sql_command);
					ResultSet result = preStatement.executeQuery();
				
				    while(result.next()){
				    	people_details += "<p><b> Name:  </b>"+ result.getString("first_name") + "  " + result.getString("last_name")+ "</p>" ;
				    	people_details += "<p><b> Address: </b>"   + result.getString("street_address") + "</p>";
				    	people_details += "<p> " + result.getString("city") +", " + result.getString("state") + "   " + result.getInt( "zip_code") + "</p>";
				    	people_details += "<p><b> Email Address: </b>"   + result.getString("email_address") + "</p>";
				    	people_details += "<p><b> Position: </b>"   + result.getString("position") + "</p>";
				    	people_details += "<p><b> Company: </b>"   + result.getString("company") + "</p>";
				    	
				    }
				    
				    conn.close();
			  	}catch (SQLException e) {
						// TODO Auto-generated catch block
					e.printStackTrace();
				}
			  request.setAttribute("people_details",people_details);
			  getServletContext().getRequestDispatcher("/People_details.jsp").forward(request, response);
		  }

		   public void destroy() 
		   { 
		     // do nothing. 
		   } 
}