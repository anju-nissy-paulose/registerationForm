package group7.term3.assignment3;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Servlet implementation class RegistrationController
 */
public class RegistrationController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistrationController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String name = request.getParameter("fullname");
        String userName = request.getParameter("userName");
        String pass = request.getParameter("pass");
        String addr = request.getParameter("address");
        String age = request.getParameter("age");
        String qual = request.getParameter("qual");
        String percent = request.getParameter("percent");
        String year = request.getParameter("yop");

        // validate given input
        if (name.isEmpty() || addr.isEmpty() || age.isEmpty() || qual.isEmpty() || percent.isEmpty() || year.isEmpty()) {
            RequestDispatcher rd = request.getRequestDispatcher("registration.jsp");
            out.println("<font color=red>Please fill all the fields</font>");
            rd.include(request, response);
        } else {
            // inserting data into mysql(mariadb) database
            // create a test database and student table before running this to create table
            //create table student(name varchar(100), userName varchar(100), pass varchar(100), addr varchar(100), age int, qual varchar(100), percent varchar(100), year varchar(100));
            try {
            	Class.forName("com.mysql.jdbc.Driver");  
            	Connection con=DriverManager.getConnection(  
            	"jdbc:mysql://localhost:3306/world","root","root");  
                 String query = "insert into student values(?,?,?,?,?,?,?,?)";

                 PreparedStatement ps = con.prepareStatement(query); // generates sql query

                ps.setString(1, name);
                ps.setString(2, userName);
                ps.setString(3, pass);
                ps.setString(4, addr);
                ps.setInt(5, Integer.parseInt(age));
                ps.setString(6, qual);
                ps.setString(7, percent);
                ps.setString(8, year);

                ps.executeUpdate(); // execute it on test database
                System.out.println("successfuly inserted");
                ps.close();
                con.close();
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
            RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
            rd.forward(request, response);
        }
    }
}