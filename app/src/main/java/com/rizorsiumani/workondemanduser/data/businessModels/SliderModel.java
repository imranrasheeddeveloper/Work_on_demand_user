package com.rizorsiumani.workondemanduser.data.businessModels;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class SliderModel{

	@SerializedName("data")
	private List<SliderDataItem> sliderData;

	@SerializedName("success")
	private boolean success;

	@SerializedName("message")
	private String message;

	public List<SliderDataItem> getSliderData(){
		return sliderData;
	}

	public boolean isSuccess(){
		return success;
	}

	public String getMessage(){
		return message;
	}
}