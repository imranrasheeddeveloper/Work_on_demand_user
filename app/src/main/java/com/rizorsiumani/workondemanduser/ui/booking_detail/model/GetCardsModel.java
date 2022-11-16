package com.rizorsiumani.workondemanduser.ui.booking_detail.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class GetCardsModel{

	@SerializedName("data")
	private List<DataItem> data;

	@SerializedName("has_more")
	private boolean hasMore;

	@SerializedName("url")
	private String url;

	@SerializedName("object")
	private String object;

	public List<DataItem> getData(){
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