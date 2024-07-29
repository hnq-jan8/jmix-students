package com.school.secondjmix.view.school;

import com.school.secondjmix.entity.School;
import com.school.secondjmix.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.EditedEntityContainer;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "schools/:id", layout = MainView.class)
@ViewController("School.detail")
@ViewDescriptor("school-detail-view.xml")
@EditedEntityContainer("schoolDc")
public class SchoolDetailView extends StandardDetailView<School> {
}
