package rpc;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import db.DBConnection;
import db.DBConnectionFactory;

/**
 * Servlet implementation class UpdateBid
 */
@WebServlet("/UpdateBid")
public class UpdateBid extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateBid() {
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
		DBConnection conn = DBConnectionFactory.getConnection();

		try {
			JSONObject input = RpcHelper.readJsonObject(request);
			int ad_id = input.getInt("ad_id");
			double bid = input.getDouble("bid");
			conn.updateBid(ad_id, bid);
			
			RpcHelper.writeJsonObject(response,
					new JSONObject().put("result", "SUCCESS"));

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}

	}

}
