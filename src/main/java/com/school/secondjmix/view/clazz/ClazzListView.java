package com.school.secondjmix.view.clazz;

import com.school.secondjmix.entity.Clazz;
import com.school.secondjmix.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.DialogMode;
import io.jmix.flowui.view.LookupComponent;
import io.jmix.flowui.view.StandardListView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;


@Route(value = "classes", layout = MainView.class)
@ViewController("Clazz.list")
@ViewDescriptor("clazz-list-view.xml")
@LookupComponent("clazzesDataGrid")
@DialogMode(width = "64em")
public class ClazzListView extends StandardListView<Clazz> {
}
