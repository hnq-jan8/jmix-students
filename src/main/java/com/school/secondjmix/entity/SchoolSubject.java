package com.school.secondjmix.entity;

import io.jmix.core.DeletePolicy;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.entity.annotation.OnDeleteInverse;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@JmixEntity
@Table(name = "SCHOOL_SUBJECT", indexes = {
        @Index(name = "IDX_SCHOOL_SUBJECT_SCHOOL", columnList = "SCHOOL_ID"),
        @Index(name = "IDX_SCHOOL_SUBJECT_SUBJECT", columnList = "SUBJECT_ID")
})
@Entity
public class SchoolSubject {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @Column(name = "VERSION", nullable = false)
    @Version
    private Integer version;

    @OnDeleteInverse(DeletePolicy.CASCADE)
    @NotNull
    @JoinColumn(name = "SCHOOL_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private School school;

    @OnDeleteInverse(DeletePolicy.DENY)
    @JoinColumn(name = "SUBJECT_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Subject subject;

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}