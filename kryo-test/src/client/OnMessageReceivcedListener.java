package client;

import message.ChatMessage;

public interface OnMessageReceivcedListener {
	public void onMessageReceived(ChatMessage message);
	public void setServerName(String name);
}
