package com.school.secondjmix.security;

import com.school.secondjmix.entity.Clazz;
import com.school.secondjmix.entity.School;
import com.school.secondjmix.entity.Student;
import com.school.secondjmix.entity.Teacher;
import io.jmix.security.model.EntityAttributePolicyAction;
import io.jmix.security.model.EntityPolicyAction;
import io.jmix.security.role.annotation.EntityAttributePolicy;
import io.jmix.security.role.annotation.EntityPolicy;
import io.jmix.security.role.annotation.ResourceRole;
import io.jmix.securityflowui.role.annotation.MenuPolicy;
import io.jmix.securityflowui.role.annotation.ViewPolicy;

@ResourceRole(name = "School Role: Can see corresponding teachers, classes and students", code = SchoolRole.CODE, scope = "UI")
public interface SchoolRole {
    String CODE = "school";

    @MenuPolicy(menuIds = {"Clazz.list", "Student.list", "Teacher.list"})
    @ViewPolicy(viewIds = {"Clazz.list", "Student.list", "Teacher.list", "Student.detail", "Teacher.detail", "Clazz.detail", "School.detail"})
    void screens();

    @EntityAttributePolicy(entityClass = Student.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Student.class, actions = EntityPolicyAction.ALL)
    void student();

    @EntityAttributePolicy(entityClass = Teacher.class, attributes = {"id", "version", "name", "dateOfBirth", "phoneNumber", "address"}, action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Teacher.class, actions = EntityPolicyAction.ALL)
    void teacher();

    @EntityPolicy(entityClass = School.class, actions = EntityPolicyAction.READ)
    @EntityAttributePolicy(entityClass = School.class, attributes = {"version", "code", "name"}, action = EntityAttributePolicyAction.VIEW)
    void school();

    @EntityAttributePolicy(entityClass = Clazz.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Clazz.class, actions = EntityPolicyAction.ALL)
    void clazz();
}
