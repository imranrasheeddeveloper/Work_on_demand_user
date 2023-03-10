package com.rizorsiumani.user.data.businessModels.stripe;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class EphemeralKeyResponse{

	@SerializedName("associated_objects")
	private List<AssociatedObjectsItem> associatedObjects;

	@SerializedName("expires")
	private int expires;

	@SerializedName("livemode")
	private boolean livemode;

	@SerializedName("created")
	private int created;

	@SerializedName("id")
	private String id;

	@SerializedName("secret")
	private String secret;

	@SerializedName("object")
	private String object;

	public List<AssociatedObjectsItem> getAssociatedObjects(){
		return associatedObjects;
	}

	public int getExpires(){
		return expires;
	}

	public boolean isLivemode(){
		return livemode;
	}

	public int getCreated(){
		return created;
	}

	public String getId(){
		return id;
	}

	public String getSecret(){
		return secret;
	}

	public String getObject(){
		return object;
	}
}