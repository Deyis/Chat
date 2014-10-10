package com.hifive.common.model;


public abstract class AbstractModel implements BaseModel {

    @Override
    public boolean equals(Object obj) {
        return this.getClass().equals(obj.getClass()) && getId().equals(((BaseModel) obj).getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }
}
