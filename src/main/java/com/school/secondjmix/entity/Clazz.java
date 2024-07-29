package com.school.secondjmix.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.DependsOnProperties;
import io.jmix.core.metamodel.annotation.InstanceName;
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

import java.time.LocalDate;
import java.util.UUID;

@JmixEntity
@Table(name = "CLASS", indexes = {
        @Index(name = "IDX_CLASS_TEACHER", columnList = "TEACHER_ID"),
        @Index(name = "IDX_CLASS_SCHOOL", columnList = "SCHOOL_ID")
})
@Entity
public class Clazz {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @Column(name = "VERSION", nullable = false)
    @Version
    private Integer version;

    @NotNull
    @Column(name = "NAME", nullable = false)
    private String name;

    @JoinColumn(name = "TEACHER_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Teacher teacher;

    @NotNull
    @JoinColumn(name = "SCHOOL_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private School school;

    @NotNull
    @Column(name = "START_AT", nullable = false)
    private LocalDate startAt;

    @Column(name = "END_AT")
    private LocalDate endAt;

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public void setEndAt(LocalDate endAt) {
        this.endAt = endAt;
    }

    public LocalDate getEndAt() {
        return endAt;
    }

    public void setStartAt(LocalDate startAt) {
        this.startAt = startAt;
    }

    public LocalDate getStartAt() {
        return startAt;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @InstanceName
    @DependsOnProperties({"name", "school"})
    public String getInstanceName() {
        return school.getCode() + " - " + name;
    }
}
