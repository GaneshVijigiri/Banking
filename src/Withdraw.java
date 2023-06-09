import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.GenericServlet;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
@WebServlet("/Withdraw")
public class Withdraw extends GenericServlet
{
	@Override
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
		String accountNumber = req.getParameter("aN");
		String phoneNumber = req.getParameter("pN");
		String tempWithdraw = req.getParameter("wA");
		double withdraw = Double.parseDouble(tempWithdraw);
		String tempPin = req.getParameter("pinN");
		int pin = Integer.parseInt(tempPin);
		
		
		String url = "jdbc:mysql://localhost:3306/tejm32?user=root&password=12345";
		String select = "select * from banking where AccountNumber=? and PinNumber=? and PhoneNumber=?";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connect = DriverManager.getConnection(url);
			PreparedStatement ps = connect.prepareStatement(select);
			
			ps.setString(1, accountNumber);
			ps.setInt(2,pin);
			ps.setString(3, phoneNumber);
			ResultSet rs = ps.executeQuery();
			PrintWriter pw = res.getWriter();
			if(rs.next())
			{
				double amount = rs.getDouble(4);
				if(amount<withdraw)
				{
					RequestDispatcher rd = req.getRequestDispatcher("Withdraw.html");
					rd.include(req, res);
					pw.write("<p style='color:red'>Insufficient Balance</p>");
				}
				else if(withdraw<0)
				{
					RequestDispatcher rd = req.getRequestDispatcher("Withdraw.html");
					rd.include(req, res);
					pw.write("<p style='color:red'>Incorrect number</p>");
				}
				else
				{
					double balance = amount-withdraw;
					String update = "update banking set Amount="+balance+ "where AccountNumber="+accountNumber+" and PinNumber="+pin;
					int num = ps.executeUpdate(update);
					if(num!=0)
					{
						pw.println("Amount withdrawl successfully <br>");
						pw.println("Remaining balance is : "+balance+"/-");
					}
					else {
						pw.println("Transaction failed");
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
