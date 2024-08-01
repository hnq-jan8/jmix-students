package com.school.secondjmix.view.student;

import com.school.secondjmix.entity.Clazz;
import com.school.secondjmix.entity.Student;
import com.school.secondjmix.view.main.MainView;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyDownEvent;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.component.combobox.EntityComboBox;
import io.jmix.flowui.component.datepicker.TypedDatePicker;
import io.jmix.flowui.component.textfield.TypedTextField;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.model.CollectionLoader;
import io.jmix.flowui.view.DialogMode;
import io.jmix.flowui.view.LookupComponent;
import io.jmix.flowui.view.StandardListView;
import io.jmix.flowui.view.Subscribe;
import io.jmix.flowui.view.ViewComponent;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

import java.time.LocalDate;


@Route(value = "students", layout = MainView.class)
@ViewController("Student.list")
@ViewDescriptor("student-list-view.xml")
@LookupComponent("studentsDataGrid")
@DialogMode(width = "64em")
public class StudentListView extends StandardListView<Student> {

    @ViewComponent
    private CollectionLoader<Student> studentsDl;

    @ViewComponent
    private TypedTextField<String> nameField;
    @ViewComponent
    private TypedDatePicker<LocalDate> dateOfBirthField;
    @ViewComponent
    private TypedTextField<String> phoneNumberField;
    @ViewComponent
    private TypedTextField<String> addressField;
    @ViewComponent
    private TypedTextField<String> citizenIdField;
    @ViewComponent
    private EntityComboBox<Clazz> clazzField;

    @Subscribe(id = "searchButton", subject = "clickListener")
    public void onSearchButtonClick(final ClickEvent<JmixButton> event) {
        search();
    }

    @Subscribe(id = "clearButton", subject = "clickListener")
    public void onClearButtonClick(final ClickEvent<JmixButton> event) {
        nameField.clear();
        dateOfBirthField.clear();
        phoneNumberField.clear();
        addressField.clear();
        citizenIdField.clear();
        clazzField.clear();

        search();
    }

    @Subscribe("nameField")
    public void onNameFieldKeyDown(final KeyDownEvent event) {
        handleEnterKeyDown(event);
    }

    @Subscribe("dateOfBirthField")
    public void onDateOfBirthFieldComponentValueChange(
            final AbstractField.ComponentValueChangeEvent<TypedDatePicker<LocalDate>, LocalDate> event) {
        search();
    }

    @Subscribe("phoneNumberField")
    public void onPhoneNumberFieldKeyDown(final KeyDownEvent event) {
        handleEnterKeyDown(event);
    }

    @Subscribe("addressField")
    public void onAddressFieldKeyDown(final KeyDownEvent event) {
        handleEnterKeyDown(event);
    }

    @Subscribe("citizenIdField")
    public void onCitizenIdFieldKeyDown(final KeyDownEvent event) {
        handleEnterKeyDown(event);
    }

    @Subscribe("clazzField")
    public void onClazzFieldComponentValueChange(final AbstractField.ComponentValueChangeEvent<EntityComboBox<Clazz>, Clazz> event) {
        search();
    }


    private void search() {
        removeAllParams();
        String query = "select e from Student e";

        if (nameField.getValue() != null) {
            query += " where e.name like concat('%', :name, '%')";
            studentsDl.setParameter("name", nameField.getValue());
        }
        if (dateOfBirthField.getValue() != null) {
            query += " and e.dateOfBirth = :dateOfBirth";
            studentsDl.setParameter("dateOfBirth", dateOfBirthField.getValue());
        }
        if (phoneNumberField.getValue() != null) {
            query += " and e.phoneNumber like concat('%', :phoneNumber, '%')";
            studentsDl.setParameter("phoneNumber", phoneNumberField.getValue());
        }
        if (addressField.getValue() != null) {
            query += " and e.address like concat('%', :address, '%')";
            studentsDl.setParameter("address", addressField.getValue());
        }
        if (citizenIdField.getValue() != null) {
            query += " and e.citizenId like concat('%', :citizenId, '%')";
            studentsDl.setParameter("citizenId", citizenIdField.getValue());
        }
        if (clazzField.getValue() != null) {
            query += " and e.clazz.id = :clazzId";
            studentsDl.setParameter("clazzId", clazzField.getValue().getId());
        }

        studentsDl.setQuery(query);
        studentsDl.load();
    }

    private void removeAllParams() {
        studentsDl.removeParameter("name");
        studentsDl.removeParameter("dateOfBirth");
        studentsDl.removeParameter("phoneNumber");
        studentsDl.removeParameter("address");
        studentsDl.removeParameter("citizenId");
        studentsDl.removeParameter("clazzId");
    }

    private void handleEnterKeyDown(KeyDownEvent event) {
        if (event.getKey().equals(Key.ENTER)) search();
    }
}
