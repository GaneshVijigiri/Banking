import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
@WebServlet("/ChangePin")
public class ChangePin extends GenericServlet
{
	@Override
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
		String accountNumber = req.getParameter("aN");
		String mobileNumber = req.getParameter("mN");
		String tempPin = req.getParameter("oldPin");
		int oldPin = Integer.parseInt(tempPin);
		String tempPin1 = req.getParameter("newPin");
		int newPin = Integer.parseInt(tempPin1);
		
		String url = "jdbc:mysql://localhost:3306/tejm32?user=root&password=12345";
		String update = "update banking set PinNumber=? where PinNumber=? and AccountNumber=? and PhoneNumber=?";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connect = DriverManager.getConnection(url);
			PreparedStatement ps = connect.prepareStatement(update);
			ps.setInt(1, newPin);
			ps.setInt(2, oldPin);
			ps.setString(3, accountNumber);
			ps.setString(4, mobileNumber);
			int num = ps.executeUpdate();
			PrintWriter pw = res.getWriter();
			if(num!=0)
			{
				pw.println("Pin changed successfully");
			}
			else
			{
				pw.println("Transaction failed");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
