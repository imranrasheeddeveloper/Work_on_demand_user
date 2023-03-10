package com.rizorsiumani.user.data.businessModels.stripe;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class CustomerIdResponse{

	@SerializedName("invoice_settings")
	private InvoiceSettings invoiceSettings;

	@SerializedName("metadata")
	private Metadata metadata;

	@SerializedName("test_clock")
	private Object testClock;

	@SerializedName("address")
	private Object address;

	@SerializedName("livemode")
	private boolean livemode;

	@SerializedName("default_source")
	private Object defaultSource;

	@SerializedName("invoice_prefix")
	private String invoicePrefix;

	@SerializedName("tax_exempt")
	private String taxExempt;

	@SerializedName("created")
	private int created;

	@SerializedName("next_invoice_sequence")
	private int nextInvoiceSequence;

	@SerializedName("description")
	private Object description;

	@SerializedName("discount")
	private Object discount;

	@SerializedName("preferred_locales")
	private List<Object> preferredLocales;

	@SerializedName("balance")
	private int balance;

	@SerializedName("shipping")
	private Object shipping;

	@SerializedName("phone")
	private Object phone;

	@SerializedName("delinquent")
	private boolean delinquent;

	@SerializedName("name")
	private Object name;

	@SerializedName("currency")
	private Object currency;

	@SerializedName("id")
	private String id;

	@SerializedName("email")
	private Object email;

	@SerializedName("object")
	private String object;

	public InvoiceSettings getInvoiceSettings(){
		return invoiceSettings;
	}

	public Metadata getMetadata(){
		return metadata;
	}

	public Object getTestClock(){
		return testClock;
	}

	public Object getAddress(){
		return address;
	}

	public boolean isLivemode(){
		return livemode;
	}

	public Object getDefaultSource(){
		return defaultSource;
	}

	public String getInvoicePrefix(){
		return invoicePrefix;
	}

	public String getTaxExempt(){
		return taxExempt;
	}

	public int getCreated(){
		return created;
	}

	public int getNextInvoiceSequence(){
		return nextInvoiceSequence;
	}

	public Object getDescription(){
		return description;
	}

	public Object getDiscount(){
		return discount;
	}

	public List<Object> getPreferredLocales(){
		return preferredLocales;
	}

	public int getBalance(){
		return balance;
	}

	public Object getShipping(){
		return shipping;
	}

	public Object getPhone(){
		return phone;
	}

	public boolean isDelinquent(){
		return delinquent;
	}

	public Object getName(){
		return name;
	}

	public Object getCurrency(){
		return currency;
	}

	public String getId(){
		return id;
	}

	public Object getEmail(){
		return email;
	}

	public String getObject(){
		return object;
	}
}