package db;

import java.util.List;
import entity.AdItem;



public interface DBConnection {
public void close();
	
	/**
	 * return all ads
	 * @return
	 */
	public List<AdItem> searchAdItems();
	
	/**
	 * Given an advertiser_id, return budget of this advertiser
	 * @param advertiser_id
	 * @return
	 */
	public float getBudget(int advertiser_id);
	
	/**
	 * Update advertiser budget
	 * @param advertiser_id
	 * @param budget
	 */
	public void updateBudget(int advertiser_id, double budget);
	
	/**
	 * Update ad bid 
	 * @param ad_id
	 * @param bid
	 */
	public void updateBid(int ad_id, double bid);
	
	/**
	 * create an advertiser
	 * @param advertiser_name
	 * @param budget
	 * @return
	 */
	public long createAdvertiser(String advertiser_name, double budget);
	
	/**
	 * create an ad
	 * @param bid
	 * @param image_url
	 * @param advertiser_id
	 * @param ad_score
	 * @return
	 */
	public long createAd(double bid, String image_url, int advertiser_id, double ad_score);

}
