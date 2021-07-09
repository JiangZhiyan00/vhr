package com.jiangzhiyan.vhr.model;

import java.util.Date;

/**
 * 聊天消息对象
 * @author JiangZhiyan
 */
public class ChatMessage {

    /**消息来源*/
    private String from;
    /**消息去处*/
    private String to;
    /**消息内容*/
    private String content;
    /**消息发送时间*/
    private Date sendTime;
    /**消息发送者的姓名*/
    private String fromNickname;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getFromNickname() {
        return fromNickname;
    }

    public void setFromNickname(String fromNickname) {
        this.fromNickname = fromNickname;
    }
}
