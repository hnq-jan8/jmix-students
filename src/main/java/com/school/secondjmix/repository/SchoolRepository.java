package com.school.secondjmix.repository;

import com.school.secondjmix.entity.School;
import io.jmix.core.repository.JmixDataRepository;

import java.util.UUID;

public interface SchoolRepository extends JmixDataRepository<School, UUID> {
}
