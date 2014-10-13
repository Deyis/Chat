package com.hifive.chat.model;

import com.hifive.common.model.AbstractModel;
import com.hifive.common.model.BaseModel;
import com.hifive.security.model.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "conversations")
@NamedQueries({
        @NamedQuery(name = "Conversation.findFreeConversation",
                query = "select c from Conversation c where " +
                        "c.language = :language " +
                        "and c.finished != true " +
                        "and c.secondUser = null " +
                        "and c.firstUser.id != :currentUserId order by c.id asc")
})
public class Conversation extends AbstractModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="first_user_id")
    private User firstUser;

    @ManyToOne
    @JoinColumn(name="second_user_id")
    private User secondUser;

    @Column(name = "lang")
    private String language;

    @Column(name = "finished")
    private boolean finished;

    @OneToMany(mappedBy="conversation")
    private Set<Message> messages;

    @Column(name = "last_message_number")
    private Long lastMessageNumber;

    @Column(name = "creation_date")
    private Date creationDate;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getFirstUser() {
        return firstUser;
    }

    public void setFirstUser(User firstUser) {
        this.firstUser = firstUser;
    }

    public User getSecondUser() {
        return secondUser;
    }

    public void setSecondUser(User secondUser) {
        this.secondUser = secondUser;
    }

    public Set<Message> getMessages() {
        return messages;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public Long getLastMessageNumber() {
        return lastMessageNumber;
    }

    public void setLastMessageNumber(Long lastMessageNumber) {
        this.lastMessageNumber = lastMessageNumber;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}