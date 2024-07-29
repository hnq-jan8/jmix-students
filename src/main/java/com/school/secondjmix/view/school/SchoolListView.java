package com.school.secondjmix.view.school;

import com.school.secondjmix.entity.School;
import com.school.secondjmix.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.DialogMode;
import io.jmix.flowui.view.LookupComponent;
import io.jmix.flowui.view.StandardListView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;


@Route(value = "schools", layout = MainView.class)
@ViewController("School.list")
@ViewDescriptor("school-list-view.xml")
@LookupComponent("schoolsDataGrid")
@DialogMode(width = "64em")
public class SchoolListView extends StandardListView<School> {
}
