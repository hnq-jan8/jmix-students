package com.school.secondjmix.security;

import com.school.secondjmix.entity.Clazz;
import com.school.secondjmix.entity.Student;
import io.jmix.security.role.annotation.JpqlRowLevelPolicy;
import io.jmix.security.role.annotation.RowLevelRole;

@RowLevelRole(name = "TeacherRl", code = TeacherRlRole.CODE)
public interface TeacherRlRole {
    String CODE = "teacher-rl";

    @JpqlRowLevelPolicy(entityClass = Clazz.class, where = "{E}.teacher.account.id = :current_user_id")
    void clazz();

    @JpqlRowLevelPolicy(entityClass = Student.class, where = "{E}.clazz.teacher.account.id = :current_user_id")
    void student();
}
