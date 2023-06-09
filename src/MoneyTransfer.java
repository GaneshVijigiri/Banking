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
@WebServlet("/MoneyTransfer")
public class MoneyTransfer extends GenericServlet
{
	@Override
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
		String tempPin = req.getParameter("pN");
		int pin = Integer.parseInt(tempPin);
		String fromAccountNumber = req.getParameter("faN");
		String toAccountNumber = req.getParameter("taN");
		String mobileNumber = req.getParameter("mN");
		String tempAmount = req.getParameter("amount");
		double amount = Double.parseDouble(tempAmount);
		
		String url = "jdbc:mysql://localhost:3306/tejm32?user=root&password=12345";
		String select = "select * from banking where AccountNumber=? and PinNumber=?";
		
		String select1 = "select * from banking where AccountNumber=? and PhoneNumber=?";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connect = DriverManager.getConnection(url);
			PreparedStatement ps = connect.prepareStatement(select);
			ps.setString(1, fromAccountNumber);
			ps.setInt(2, pin);
			ResultSet rs  = ps.executeQuery();
			
			PreparedStatement ps1 = connect.prepareStatement(select1);
			ps1.setString(1, toAccountNumber);
			ps1.setString(2, mobileNumber);
			ResultSet rs1  = ps1.executeQuery();
			
			PrintWriter pw = res.getWriter();
			if(rs.last())
			{
				double money = rs.getDouble(4);
				double balance = money-amount;
				
				String query = "update banking set Amount="+balance+"where AccountNumber="+fromAccountNumber+" and PinNumber="+pin;
				int num = ps.executeUpdate(query);
				if(num!=0)
				{
					pw.println("Money Transferred successfully <br>");
					pw.println("Remaining balance : "+balance+"/-");
				}
				else
				{
					pw.println("Transaction failed");
				}
				if(rs1.last())
				{
					double money1 = rs1.getDouble(4);
					double balance1 = money1+amount;
					String query1 = "update banking set Amount="+balance1+"where AccountNumber="+toAccountNumber+" and PhoneNumber="+mobileNumber;
					int num1=ps1.executeUpdate(query1);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
