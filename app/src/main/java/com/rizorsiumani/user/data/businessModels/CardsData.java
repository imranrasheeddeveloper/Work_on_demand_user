package com.rizorsiumani.user.data.businessModels;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class CardsData {

	@SerializedName("data")
	private List<CardsDataItem> data;

	@SerializedName("has_more")
	private boolean hasMore;

	@SerializedName("url")
	private String url;

	@SerializedName("object")
	private String object;

	public List<CardsDataItem> getData(){
		return data;
	}

	public boolean isHasMore(){
		return hasMore;
	}

	public String getUrl(){
		return url;
	}

	public String getObject(){
		return object;
	}
}