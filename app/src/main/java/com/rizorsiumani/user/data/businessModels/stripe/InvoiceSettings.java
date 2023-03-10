package com.rizorsiumani.user.data.businessModels.stripe;

import com.google.gson.annotations.SerializedName;

public class InvoiceSettings{

	@SerializedName("footer")
	private Object footer;

	@SerializedName("custom_fields")
	private Object customFields;

	@SerializedName("default_payment_method")
	private Object defaultPaymentMethod;

	@SerializedName("rendering_options")
	private Object renderingOptions;

	public Object getFooter(){
		return footer;
	}

	public Object getCustomFields(){
		return customFields;
	}

	public Object getDefaultPaymentMethod(){
		return defaultPaymentMethod;
	}

	public Object getRenderingOptions(){
		return renderingOptions;
	}
}