package com.rizorsiumani.user.data.businessModels.chatwoot_model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Meta{

	@SerializedName("contact")
	private Contact contact;

	@SerializedName("assignee")
	private Assignee assignee;

	@SerializedName("agent_last_seen_at")
	private String agentLastSeenAt;

	@SerializedName("assignee_last_seen_at")
	private String assigneeLastSeenAt;

	@SerializedName("labels")
	private List<Object> labels;

	@SerializedName("additional_attributes")
	private AdditionalAttributes additionalAttributes;

	public Contact getContact(){
		return contact;
	}

	public Assignee getAssignee(){
		return assignee;
	}

	public String getAgentLastSeenAt(){
		return agentLastSeenAt;
	}

	public String getAssigneeLastSeenAt(){
		return assigneeLastSeenAt;
	}

	public List<Object> getLabels(){
		return labels;
	}

	public AdditionalAttributes getAdditionalAttributes(){
		return additionalAttributes;
	}
}