package com.rizorsiumani.workondemanduser.data.businessModels.job_timing;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class JobTimingDataItem {

	@SerializedName("createdAt")
	private String createdAt;

	@SerializedName("id")
	private int id;

	@SerializedName("day")
	private String day;

	@SerializedName("job_days")
	private List<JobDaysItem> jobDays;

	@SerializedName("updatedAt")
	private String updatedAt;

	public String getCreatedAt(){
		return createdAt;
	}

	public int getId(){
		return id;
	}

	public String getDay(){
		return day;
	}

	public List<JobDaysItem> getJobDays(){
		return jobDays;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}
}