package suvvm.servlet;

import java.util.List;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import suvvm.inf.Worker;
import suvvm.utils.DB;

/**
 * Servlet implementation class GetWorkerServlet
 */
@WebServlet("/GetWorkerServlet")
public class GetWorkerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetWorkerServlet() {
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
		int shopid = Integer.parseInt(request.getParameter("shopid"));
		String sql = "SELECT * FROM `workers_"+ shopid +"`";
		try {
			ResultSet rSet = DB.executeQuery(sql);
			List<Worker> list = new ArrayList<Worker>();
			while(rSet.next()) {
				String sql2 = "SELECT * FROM user WHERE id="+rSet.getInt("id")+"";
				ResultSet rSet2 = DB.executeQuery(sql2);
				if(rSet2.next()) {
					list.add(new Worker(rSet2.getInt("id"), rSet2.getString("name"), rSet2.getString("pnum")));
				}
			}
			JSONArray obj = JSON.parseArray(JSON.toJSONString(list));
	        out.print(obj);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			out.print("error");
		}
	}

}
