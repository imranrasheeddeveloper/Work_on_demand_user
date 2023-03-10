package com.rizorsiumani.user.data.businessModels.stripe;

import com.google.gson.annotations.SerializedName;

public class PaymentMethodOptions{

	@SerializedName("giropay")
	private Giropay giropay;

	@SerializedName("bancontact")
	private Bancontact bancontact;

	@SerializedName("ideal")
	private Ideal ideal;

	@SerializedName("link")
	private Link link;

	@SerializedName("eps")
	private Eps eps;

	@SerializedName("card")
	private Card card;

	public Giropay getGiropay(){
		return giropay;
	}

	public Bancontact getBancontact(){
		return bancontact;
	}

	public Ideal getIdeal(){
		return ideal;
	}

	public Link getLink(){
		return link;
	}

	public Eps getEps(){
		return eps;
	}

	public Card getCard(){
		return card;
	}
}