package com.rizorsiumani.workondemanduser.data.businessModels.job_timing;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class JobTimingModel{

	@SerializedName("data")
	private List<JobTimingDataItem> data;

	@SerializedName("success")
	private boolean success;

	@SerializedName("message")
	private String message;

	public List<JobTimingDataItem> getData(){
		return data;
	}

	public boolean isSuccess(){
		return success;
	}

	public String getMessage(){
		return message;
	}
}