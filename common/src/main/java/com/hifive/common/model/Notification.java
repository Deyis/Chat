package com.hifive.common.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name = "notifications")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public class Notification extends AbstractModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "text")
    private String text;

    @Column(name = "pending")
    private Boolean pending;

    @Column(name = "accepted")
    private Boolean accepted;

    @Column(name = "type", insertable = false, updatable = false)
    private String type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean getPending() {
        return pending;
    }


    public void setPending(Boolean pending) {
        this.pending = pending;
    }

    public String getType() {
        return type;
    }

    public Type getProperType() {
        return Type.find(type);
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setType(Type type) {
        this.type = type.toString();
    }

    public Boolean getAccepted() {
        return accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }


    public enum Type {
        BASE("BASE"), FRIENDSHIP("FRIENDSHIP");

        private  String value;

        Type(String value){
            this.value = value;
        }

        public String toString() {
            return value;
        }

        public static Type find(String value) {
            for (Type type: Type.values()) {
                if (type.value.equals(value)) {
                    return type;
                }
            }
            return null;
        }

    }
}
