package com.aneta.services.scripting.entity;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @CreatedDate
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Date createdAt;

  @LastModifiedDate
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Date updatedAt;

  @CreatedBy
  @GeneratedValue(strategy = GenerationType.AUTO)
  private String createdBy;

  @LastModifiedBy
  @GeneratedValue(strategy = GenerationType.AUTO)
  private String updatedBy;

  public BaseEntity() {}

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

  public String getUpdatedBy() {
    return updatedBy;
  }

  public void setUpdatedBy(String updatedBy) {
    this.updatedBy = updatedBy;
  }

  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }
}
