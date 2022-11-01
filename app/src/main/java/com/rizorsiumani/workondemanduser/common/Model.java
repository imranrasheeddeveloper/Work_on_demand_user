package com.rizorsiumani.workondemanduser.common;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Model{

	@SerializedName("data")
	private List<AddBookingDataItem> data;

	public List<AddBookingDataItem> getData(){
		return data;
	}
}