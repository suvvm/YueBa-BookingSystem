package suvvm.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import suvvm.utils.DB;
import suvvm.utils.MD5;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setHeader("Access-Control-Allow-Origin", "*"); 
        response.setContentType("application/json; charset=UTF-8");
        PrintWriter out = response.getWriter();
        String name = request.getParameter("name");
        String pnum = request.getParameter("pnum");
        String password = request.getParameter("password");
        String img = request.getParameter("img");
        password = MD5.getMd5(password);
        String sql = "INSERT INTO user(name,pnum,password,img) VALUE('"+name+"', '"+pnum+"', '"+password+"','"+img+"')";
        System.out.println(sql);
        try {
            int result = DB.executeUpdate(sql);
            if (result > 0) {
                out.println("success");
            }else {
                out.println("error");
            }
        }catch (Exception ex) {
            log(ex.getMessage());
            out.println("error");
        }
	}

}
