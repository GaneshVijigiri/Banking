import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
@WebServlet("/BalanceEnquiry")
public class BalaceEnquiry extends GenericServlet
{
	@Override
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException 
	{
		String tempPin = req.getParameter("pN");
		int pin = Integer.parseInt(tempPin);
		String accountNumber = req.getParameter("aN");
		
		String url = "jdbc:mysql://localhost:3306/tejm32?user=root&password=12345";
		String select = "select * from banking where PinNumber=? and AccountNumber=?";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connect = DriverManager.getConnection(url);
			PreparedStatement ps = connect.prepareStatement(select);
			ps.setInt(1, pin);
			ps.setString(2, accountNumber);
			ResultSet rs = ps.executeQuery();
			PrintWriter pw = res.getWriter();
			if(rs.next())
			{
				pw.println("Balance : "+rs.getDouble(4)+"/-");
			}
			else {
				System.err.println("Incorrect Pin or account number");
			}
				
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
