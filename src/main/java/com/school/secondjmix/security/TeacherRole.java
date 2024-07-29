package com.school.secondjmix.security;

import com.school.secondjmix.entity.Clazz;
import com.school.secondjmix.entity.Student;
import io.jmix.security.model.EntityAttributePolicyAction;
import io.jmix.security.model.EntityPolicyAction;
import io.jmix.security.role.annotation.EntityAttributePolicy;
import io.jmix.security.role.annotation.EntityPolicy;
import io.jmix.security.role.annotation.ResourceRole;
import io.jmix.securityflowui.role.annotation.MenuPolicy;
import io.jmix.securityflowui.role.annotation.ViewPolicy;

@ResourceRole(name = "Teacher Role: Can see whose classes and students, read only", code = TeacherRole.CODE, scope = "UI")
public interface TeacherRole {
    String CODE = "teacher";

    @MenuPolicy(menuIds = {"Clazz.list", "Student.list"})
    @ViewPolicy(viewIds = {"Clazz.list", "Student.list", "Clazz.detail", "Student.detail"})
    void screens();

    @EntityAttributePolicy(entityClass = Student.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = Student.class, actions = EntityPolicyAction.READ)
    void student();

    @EntityAttributePolicy(entityClass = Clazz.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = Clazz.class, actions = EntityPolicyAction.READ)
    void clazz();
}
