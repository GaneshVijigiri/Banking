import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/LogIn")
public class LogIn extends HttpServlet
{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String account = req.getParameter("account");
		String password = req.getParameter("password");
		
		String url = "jdbc:mysql://localhost:3306/tejm32?user=root&password=12345";
		String select = "select * from banking where AccountNumber=? and Password=?";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connect = DriverManager.getConnection(url);
			PreparedStatement ps = connect.prepareStatement(select);
			ps.setString(1, account);
			ps.setString(2, password);
			
			ResultSet rs = ps.executeQuery();
			PrintWriter pw = resp.getWriter();
			if(rs.next())
			{
				RequestDispatcher rd = req.getRequestDispatcher("Banking.html");
				rd.forward(req, resp);
			}
			else
			{
				RequestDispatcher rd = req.getRequestDispatcher("login.html");
				rd.include(req, resp);
				pw.println("Incorrect Account number or password");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
