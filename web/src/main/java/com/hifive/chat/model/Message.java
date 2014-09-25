package com.hifive.chat.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hifive.security.model.User;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "messages")
@NamedQueries({
        @NamedQuery(name = "Message.getMessages",
                query = "select m from Message m where " +
                        "m.messageNumber > :lastMessageNumber " +
                        "and m.conversation.id = :conversationId order by messageNumber asc ")
})
public class Message implements BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="conversation_id")
    private Conversation conversation;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id")
    private User user;

    @Column(name="message")
    private String message;

    @Column(name = "creation_date")
    private Date creationDate;

    @Column(name = "message_number")
    private Long messageNumber;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Long getMessageNumber() {
        return messageNumber;
    }

    public void setMessageNumber(Long messageNumber) {
        this.messageNumber = messageNumber;
    }
}
