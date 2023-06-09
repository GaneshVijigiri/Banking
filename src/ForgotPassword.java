import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/ForgotPassword")
public class ForgotPassword extends HttpServlet
{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String account = req.getParameter("account");
		String password = req.getParameter("password");
		String confirmPassword = req.getParameter("password1");
		PrintWriter pw = resp.getWriter();
		if(password.equals(confirmPassword))
		{
			String url = "jdbc:mysql://localhost:3306/tejm32?user=root&password=12345";
			String update = "update banking set Password=? where AccountNumber=?";
			
			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection connect = DriverManager.getConnection(url);
				PreparedStatement ps = connect.prepareStatement(update);
				ps.setString(1, password);
				ps.setString(2, account);
				
				int num = ps.executeUpdate();
				if(num!=0)
				{
					pw.println("Password changed");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			RequestDispatcher rd = req.getRequestDispatcher("forgotpassword.html");
			rd.include(req, resp);
			pw.write("Password mismatch");
		}
	}
}
