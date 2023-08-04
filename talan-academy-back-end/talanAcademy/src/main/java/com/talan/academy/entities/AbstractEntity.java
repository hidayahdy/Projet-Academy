package com.talan.academy.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AbstractEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime creationDate;

    @LastModifiedDate
    @Column(name = "modified_at")
    private LocalDateTime lastModifiedDate;
    
    @CreatedBy
    @Column(name = "created_By", nullable = false)
    private String createdBy;

    @LastModifiedBy
    @Column(name = "modified_By")
    private String lastModifiedBy;
    

}
