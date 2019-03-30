package com.refugees.portal.db.health.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

public class MedicalCheckupInterviewsPK implements Serializable {
    private Object resourceKey;
    private Timestamp referenceDatetime;
    private String itemClassCode;
    private String derivedFrom;

    @Column(name = "resource_key")
    @Id
    public Object getResourceKey() {
        return resourceKey;
    }

    public void setResourceKey(Object resourceKey) {
        this.resourceKey = resourceKey;
    }

    @Column(name = "reference_datetime")
    @Id
    public Timestamp getReferenceDatetime() {
        return referenceDatetime;
    }

    public void setReferenceDatetime(Timestamp referenceDatetime) {
        this.referenceDatetime = referenceDatetime;
    }

    @Column(name = "item_class_code")
    @Id
    public String getItemClassCode() {
        return itemClassCode;
    }

    public void setItemClassCode(String itemClassCode) {
        this.itemClassCode = itemClassCode;
    }

    @Column(name = "derived_from")
    @Id
    public String getDerivedFrom() {
        return derivedFrom;
    }

    public void setDerivedFrom(String derivedFrom) {
        this.derivedFrom = derivedFrom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MedicalCheckupInterviewsPK that = (MedicalCheckupInterviewsPK) o;
        return Objects.equals(resourceKey, that.resourceKey) &&
                Objects.equals(referenceDatetime, that.referenceDatetime) &&
                Objects.equals(itemClassCode, that.itemClassCode) &&
                Objects.equals(derivedFrom, that.derivedFrom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(resourceKey, referenceDatetime, itemClassCode, derivedFrom);
    }
}
