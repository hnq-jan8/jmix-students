package com.school.secondjmix.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.Composition;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@JmixEntity
@Table(name = "STUDENT", indexes = {
        @Index(name = "IDX_STUDENT_CLAZZ", columnList = "CLASS_ID"),
        @Index(name = "IDX_STUDENT_CCCD", columnList = "CITIZEN_ID", unique = true),
        @Index(name = "IDX_STUDENT_BLOCK", columnList = "BLOCK_ID")
})
@Entity
public class Student {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @Column(name = "VERSION", nullable = false)
    @Version
    private Integer version;

    @InstanceName
    @Column(name = "NAME", nullable = false)
    @NotNull
    private String name;

    @NotNull
    @Column(name = "DATE_OF_BIRTH", nullable = false)
    private LocalDate dateOfBirth;

    @Column(name = "PHONE_NUMBER", length = 12)
    private String phoneNumber;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "CITIZEN_ID", nullable = false)
    @NotNull
    private String citizenId;

    @NotNull
    @JoinColumn(name = "CLASS_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Clazz clazz;

    @JoinColumn(name = "BLOCK_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Block block;

    @Composition
    @OneToMany(mappedBy = "student")
    private List<SubjectStudent> registeredSubjects;

    public Block getBlock() {
        return block;
    }

    public void setBlock(Block block) {
        this.block = block;
    }

    public List<SubjectStudent> getRegisteredSubjects() {
        return registeredSubjects;
    }

    public void setRegisteredSubjects(List<SubjectStudent> registeredSubjects) {
        this.registeredSubjects = registeredSubjects;
    }

    public Clazz getClazz() {
        return clazz;
    }

    public void setClazz(Clazz clazz) {
        this.clazz = clazz;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getCitizenId() {
        return citizenId;
    }

    public void setCitizenId(String citizenId) {
        this.citizenId = citizenId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
}
