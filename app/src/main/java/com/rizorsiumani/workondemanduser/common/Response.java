package com.rizorsiumani.workondemanduser.common;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Response{

	@SerializedName("categoriesFillter")
	private List<Integer> categoriesFillter;

	public List<Integer> getCategoriesFillter(){
		return categoriesFillter;
	}
}