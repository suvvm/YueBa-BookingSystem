package suvvm.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import suvvm.utils.DB;

/**
 * Servlet implementation class MdfOrderServlet
 */
@WebServlet("/MdfOrderServlet")
public class MdfOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MdfOrderServlet() {
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
		PrintWriter out = response.getWriter();
        response.setContentType("application/json; charset=UTF-8");
		int shopid = Integer.parseInt(request.getParameter("shopid"));
		int orderid = Integer.parseInt(request.getParameter("orderid"));
		String cname = request.getParameter("cname");
		String pnum = request.getParameter("pnum");
		String space = request.getParameter("space");
		String bgtime = request.getParameter("bgtime");
		String edtime = request.getParameter("edtime");
		String sql = "UPDATE `order_"+shopid+"` SET cname='"+cname+"', pnum='"+pnum+"', space='"+space+"', bgtime='"+bgtime+"', edtime='"+edtime+"'  WHERE id="+orderid+"";
		int res = DB.executeUpdate(sql);
		if(res > 0) {
			out.print("success");
		}else{
			out.print("error");
		}
	}
}
