package com.uqierp.bean;

public class Mail {

    /**
     * 邮件标题
     */
    private String title;

    /**
     * 邮件正文
     */
    private String content;

    /**
     * 目标邮箱
     */
    private String to;

    /**
     * 消息id
     */
    private String msgId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }
}
