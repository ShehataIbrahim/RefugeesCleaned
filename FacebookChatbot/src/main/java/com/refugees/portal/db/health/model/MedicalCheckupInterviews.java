package com.refugees.portal.db.health.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "medical_checkup_interviews", schema = "public", catalog = "health")
@IdClass(MedicalCheckupInterviewsPK.class)
public class MedicalCheckupInterviews {
    private Object resourceKey;
    private Timestamp referenceDatetime;
    private String itemClassCode;
    private String derivedFrom;
    private Object jsonData;
    private String createdBy;
    private Timestamp createdAt;
    private String updatedBy;
    private Timestamp updatedAt;

    @Id
    @Column(name = "resource_key")
    public Object getResourceKey() {
        return resourceKey;
    }

    public void setResourceKey(Object resourceKey) {
        this.resourceKey = resourceKey;
    }

    @Id
    @Column(name = "reference_datetime")
    public Timestamp getReferenceDatetime() {
        return referenceDatetime;
    }

    public void setReferenceDatetime(Timestamp referenceDatetime) {
        this.referenceDatetime = referenceDatetime;
    }

    @Id
    @Column(name = "item_class_code")
    public String getItemClassCode() {
        return itemClassCode;
    }

    public void setItemClassCode(String itemClassCode) {
        this.itemClassCode = itemClassCode;
    }

    @Id
    @Column(name = "derived_from")
    public String getDerivedFrom() {
        return derivedFrom;
    }

    public void setDerivedFrom(String derivedFrom) {
        this.derivedFrom = derivedFrom;
    }

    @Basic
    @Column(name = "json_data")
    public Object getJsonData() {
        return jsonData;
    }

    public void setJsonData(Object jsonData) {
        this.jsonData = jsonData;
    }

    @Basic
    @Column(name = "created_by")
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Basic
    @Column(name = "created_at")
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Basic
    @Column(name = "updated_by")
    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Basic
    @Column(name = "updated_at")
    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MedicalCheckupInterviews that = (MedicalCheckupInterviews) o;
        return Objects.equals(resourceKey, that.resourceKey) &&
                Objects.equals(referenceDatetime, that.referenceDatetime) &&
                Objects.equals(itemClassCode, that.itemClassCode) &&
                Objects.equals(derivedFrom, that.derivedFrom) &&
                Objects.equals(jsonData, that.jsonData) &&
                Objects.equals(createdBy, that.createdBy) &&
                Objects.equals(createdAt, that.createdAt) &&
                Objects.equals(updatedBy, that.updatedBy) &&
                Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(resourceKey, referenceDatetime, itemClassCode, derivedFrom, jsonData, createdBy, createdAt, updatedBy, updatedAt);
    }
}
