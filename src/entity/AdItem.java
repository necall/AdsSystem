package entity;

import org.json.JSONException;
import org.json.JSONObject;

public class AdItem {
	private int ad_id;
	private float bid;
	private String image_url;
	private int advertiser_id;
	private float ad_score;
	
	private AdItem(AdItemBuilder builder) {
		this.ad_id = builder.ad_id;
		this.bid = builder.bid;
		this.image_url = builder.image_url;
		this.advertiser_id = builder.advertiser_id;
		this.ad_score = builder.ad_score;
	}

	public static class AdItemBuilder {
		private int ad_id;
		private float bid;
		private String image_url;
		private int advertiser_id;
		private float ad_score;
		public void setAd_id(int ad_id) {
			this.ad_id = ad_id;
		}
		public void setBid(float bid) {
			this.bid = bid;
		}
		public void setImage_url(String image_url) {
			this.image_url = image_url;
		}
		public void setAdvertiser_id(int advertiser_id) {
			this.advertiser_id = advertiser_id;
		}
		public void setAd_score(float ad_score) {
			this.ad_score = ad_score;
		}
		
		public AdItem build() {
			return new AdItem(this);
		}
	}

	public int getAd_id() {
		return ad_id;
	}
	public float getBid() {
		return bid;
	}
	public String getImage_url() {
		return image_url;
	}
	public int getAdvertiser_id() {
		return advertiser_id;
	}
	public float getAd_score() {
		return ad_score;
	}
	
	public JSONObject toJSONObject() {
		JSONObject obj = new JSONObject();
		try {
			obj.put("ad_id", ad_id);
			obj.put("bid", bid);
			obj.put("image_url", image_url);
			obj.put("advertiser_id", advertiser_id);
			obj.put("ad_score", ad_score);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return obj;
	}

}
