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
@Table(name = "SUBJECT_STUDENT", indexes = {
        @Index(name = "IDX_SUBJECT_STUDENT_SUBJECT", columnList = "SUBJECT_ID"),
        @Index(name = "IDX_SUBJECT_STUDENT_STUDENT", columnList = "STUDENT_ID")
})
@Entity
public class SubjectStudent {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @Column(name = "VERSION", nullable = false)
    @Version
    private Integer version;

    @OnDeleteInverse(DeletePolicy.DENY)
    @NotNull
    @JoinColumn(name = "SUBJECT_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Subject subject;

    @OnDeleteInverse(DeletePolicy.CASCADE)
    @NotNull
    @JoinColumn(name = "STUDENT_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Student student;

    @Column(name = "DURATION")
    private Integer duration;

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
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