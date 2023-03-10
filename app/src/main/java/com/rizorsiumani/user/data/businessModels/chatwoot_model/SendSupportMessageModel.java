package com.rizorsiumani.user.data.businessModels.chatwoot_model;

import com.google.gson.annotations.SerializedName;

public class SendSupportMessageModel{

	@SerializedName("private")
	private boolean jsonMemberPrivate;

	@SerializedName("inbox_id")
	private int inboxId;

	@SerializedName("content_type")
	private String contentType;

	@SerializedName("sender")
	private Sender sender;

	@SerializedName("conversation_id")
	private int conversationId;

	@SerializedName("created_at")
	private int createdAt;

	@SerializedName("message_type")
	private int messageType;

	@SerializedName("id")
	private int id;

	@SerializedName("source_id")
	private Object sourceId;

	@SerializedName("content")
	private String content;

	@SerializedName("content_attributes")
	private ContentAttributes contentAttributes;

	public boolean isJsonMemberPrivate(){
		return jsonMemberPrivate;
	}

	public int getInboxId(){
		return inboxId;
	}

	public String getContentType(){
		return contentType;
	}

	public Sender getSender(){
		return sender;
	}

	public int getConversationId(){
		return conversationId;
	}

	public int getCreatedAt(){
		return createdAt;
	}

	public int getMessageType(){
		return messageType;
	}

	public int getId(){
		return id;
	}

	public Object getSourceId(){
		return sourceId;
	}

	public String getContent(){
		return content;
	}

	public ContentAttributes getContentAttributes(){
		return contentAttributes;
	}
}