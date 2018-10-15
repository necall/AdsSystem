package rpc;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import db.DBConnection;
import db.DBConnectionFactory;
import entity.AdItem;

/**
 * Servlet implementation class Ad
 */
@WebServlet("/Ad")
public class Ad extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Ad() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		DBConnection conn = DBConnectionFactory.getConnection();
		try {
			JSONArray array = new JSONArray();
			JSONObject ad = new JSONObject();
			AdItem adWithHighestRank = null;
			AdItem adWithSecondHighestRank = null;

			List<AdItem> items = conn.searchAdItems();

			if (items.size() < 2) {
				return;
			}

			// get first two ads
			AdItem ad0 = items.get(0);
			AdItem ad1 = items.get(1);

			float adRank0 = ad0.getAd_score() * ad0.getBid();
			float adRank1 = ad1.getAd_score() * ad1.getBid();

			if (adRank0 > adRank1) {
				adWithHighestRank = ad0;
				adWithSecondHighestRank = ad1;
			} else {
				adWithHighestRank = ad1;
				adWithSecondHighestRank = ad0;
			}

			ad = adWithHighestRank.toJSONObject();

			for (int i = 2; i < items.size(); i++) {
				AdItem item = items.get(i);
				float adRankScore = item.getAd_score() * item.getBid();

				// compute ad rank and choose highest and second highest one
				if (adRankScore > adWithHighestRank.getBid() * adWithHighestRank.getAd_score()) {
					adWithSecondHighestRank = adWithHighestRank;
					adWithHighestRank = item;
					ad = item.toJSONObject();
				} else if (adRankScore > adWithSecondHighestRank.getBid() * adWithSecondHighestRank.getAd_score()) {
					adWithSecondHighestRank = item;
				}
			}
			// calculate cost using second-price bidding
			double secondHighestAdRankScore = adWithSecondHighestRank.getBid() * adWithSecondHighestRank.getAd_score();
			double cost = secondHighestAdRankScore / adWithHighestRank.getAd_score() + 0.01;
			System.out.println("cost is:" + cost);

			// get current budget
			int advertiser_id = adWithHighestRank.getAdvertiser_id();
			double curBudget = conn.getBudget(advertiser_id);

			// update budget
			conn.updateBudget(advertiser_id, curBudget - cost);
			curBudget = conn.getBudget(advertiser_id);
			array.put(ad);
			RpcHelper.writeJsonArray(response, array);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
