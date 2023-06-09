import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Register")
public class Register extends HttpServlet
{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String name = req.getParameter("name");
		String accountNumber = req.getParameter("account");
		String mobileNumber = req.getParameter("mobile");
		String ifscCode = req.getParameter("ifsc");
		String branch = req.getParameter("branch");
		String pinTemp = req.getParameter("pin");
		int pin = Integer.parseInt(pinTemp);
		String password = req.getParameter("password");
		
		String url = "jdbc:mysql://localhost:3306/tejm32?user=root&password=12345";
		String insert = "insert into banking(AccountNumber,Name,PhoneNumber,IFSCcode,Branch,PinNumber,Password) values(?,?,?,?,?,?,?)";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connect = DriverManager.getConnection(url);
			PreparedStatement ps = connect.prepareStatement(insert);
			ps.setString(1, accountNumber);
			ps.setString(2, name);
			ps.setString(3, mobileNumber);
			ps.setString(4, ifscCode);
			ps.setString(5, branch);
			ps.setInt(6, pin);
			ps.setString(7, password);
			int num = ps.executeUpdate();
			PrintWriter pw = resp.getWriter();
			if(num!=0)
			{
				pw.println("Registered successfully");
			}
			else
			{
				pw.println("Registration not successfull");
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
