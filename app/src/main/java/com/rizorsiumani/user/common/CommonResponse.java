package com.rizorsiumani.user.common;

import com.google.gson.annotations.SerializedName;

public class CommonResponse{

	@SerializedName("success")
	private boolean success;

	@SerializedName("filePATH")
	private String filePATH;

	@SerializedName("message")
	private String message;

	public boolean isSuccess(){
		return success;
	}

	public String getFilePATH(){
		return filePATH;
	}

	public String getMessage(){
		return message;
	}
}