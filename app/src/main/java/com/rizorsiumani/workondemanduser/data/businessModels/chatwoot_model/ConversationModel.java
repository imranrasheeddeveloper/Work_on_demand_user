package com.rizorsiumani.workondemanduser.data.businessModels.chatwoot_model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ConversationModel{

	@SerializedName("contact_last_seen_at")
	private int contactLastSeenAt;

	@SerializedName("inbox_id")
	private int inboxId;

	@SerializedName("snoozed_until")
	private Object snoozedUntil;

	@SerializedName("agent_last_seen_at")
	private int agentLastSeenAt;

	@SerializedName("assignee_last_seen_at")
	private int assigneeLastSeenAt;

	@SerializedName("custom_attributes")
	private CustomAttributes customAttributes;

	@SerializedName("labels")
	private List<Object> labels;

	@SerializedName("unread_count")
	private int unreadCount;

	@SerializedName("account_id")
	private int accountId;

	@SerializedName("can_reply")
	private boolean canReply;

	@SerializedName("meta")
	private Meta meta;

	@SerializedName("messages")
	private List<Object> messages;

	@SerializedName("id")
	private int id;

	@SerializedName("muted")
	private boolean muted;

	@SerializedName("additional_attributes")
	private AdditionalAttributes additionalAttributes;

	@SerializedName("status")
	private String status;

	@SerializedName("timestamp")
	private int timestamp;

	public int getContactLastSeenAt(){
		return contactLastSeenAt;
	}

	public int getInboxId(){
		return inboxId;
	}

	public Object getSnoozedUntil(){
		return snoozedUntil;
	}

	public int getAgentLastSeenAt(){
		return agentLastSeenAt;
	}

	public int getAssigneeLastSeenAt(){
		return assigneeLastSeenAt;
	}

	public CustomAttributes getCustomAttributes(){
		return customAttributes;
	}

	public List<Object> getLabels(){
		return labels;
	}

	public int getUnreadCount(){
		return unreadCount;
	}

	public int getAccountId(){
		return accountId;
	}

	public boolean isCanReply(){
		return canReply;
	}

	public Meta getMeta(){
		return meta;
	}

	public List<Object> getMessages(){
		return messages;
	}

	public int getId(){
		return id;
	}

	public boolean isMuted(){
		return muted;
	}

	public AdditionalAttributes getAdditionalAttributes(){
		return additionalAttributes;
	}

	public String getStatus(){
		return status;
	}

	public int getTimestamp(){
		return timestamp;
	}
}