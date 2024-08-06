package com.school.secondjmix.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.Composition;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

@JmixEntity
@Table(name = "BLOCK", indexes = {
        @Index(name = "IDX_BLOCK_UNQ", columnList = "VALUE_", unique = true)
})
@Entity
public class Block {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @InstanceName
    @Column(name = "VALUE_", nullable = false, length = 10)
    @NotNull
    private String value;

    @Composition
    @OneToMany(mappedBy = "block")
    private List<BlockSubject> subjects;

    public List<BlockSubject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<BlockSubject> subjects) {
        this.subjects = subjects;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}