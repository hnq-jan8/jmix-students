package com.school.secondjmix.security;

import com.school.secondjmix.entity.Clazz;
import com.school.secondjmix.entity.School;
import com.school.secondjmix.entity.Student;
import com.school.secondjmix.entity.Subject;
import io.jmix.security.role.annotation.JpqlRowLevelPolicy;
import io.jmix.security.role.annotation.RowLevelRole;

@RowLevelRole(name = "SchoolRl", code = SchoolRlRole.CODE)
public interface SchoolRlRole {
    String CODE = "school-rl";

    @JpqlRowLevelPolicy(entityClass = School.class, where = "{E}.manager.id = :current_user_id")
    void school();

    @JpqlRowLevelPolicy(entityClass = Clazz.class, where = "{E}.school.manager.id = :current_user_id")
    void clazz();

    @JpqlRowLevelPolicy(entityClass = Student.class, where = "{E}.clazz.school.manager.id = :current_user_id")
    void student();

    @JpqlRowLevelPolicy(
            entityClass = Subject.class,
            where = "e in (select s from SchoolSubject ss join ss.subject s where ss.school.manager.id = :current_user_id)"
    )
    void subject();
}
