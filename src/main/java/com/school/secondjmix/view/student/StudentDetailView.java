package com.school.secondjmix.view.student;

import com.school.secondjmix.entity.Clazz;
import com.school.secondjmix.entity.School;
import com.school.secondjmix.entity.SchoolSubject;
import com.school.secondjmix.entity.Student;
import com.school.secondjmix.entity.Subject;
import com.school.secondjmix.entity.SubjectStudent;
import com.school.secondjmix.repository.SchoolRepository;
import com.school.secondjmix.view.main.MainView;
import com.school.secondjmix.view.subject.SubjectListView;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;
import io.jmix.core.DataManager;
import io.jmix.flowui.DialogWindows;
import io.jmix.flowui.Dialogs;
import io.jmix.flowui.action.DialogAction;
import io.jmix.flowui.component.combobox.EntityComboBox;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.kit.action.ActionVariant;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.model.CollectionPropertyContainer;
import io.jmix.flowui.model.DataContext;
import io.jmix.flowui.model.InstanceContainer;
import io.jmix.flowui.view.DialogWindow;
import io.jmix.flowui.view.EditedEntityContainer;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.Subscribe;
import io.jmix.flowui.view.Target;
import io.jmix.flowui.view.ViewComponent;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Route(value = "students/:id", layout = MainView.class)
@ViewController("Student.detail")
@ViewDescriptor("student-detail-view.xml")
@EditedEntityContainer("studentDc")
public class StudentDetailView extends StandardDetailView<Student> {
    private final List<UUID> initSubjects = new ArrayList<>();
    private final List<SubjectStudent> removedSubjects = new ArrayList<>();
    private boolean isSingleSchool = false;

    @Autowired
    private DataManager dataManager;
    @Autowired
    private DialogWindows dialogWindows;
    @Autowired
    private Dialogs dialogs;
    @Autowired
    private SchoolRepository schoolRepository;

    @ViewComponent
    private CollectionPropertyContainer<SubjectStudent> registeredSubjectsDc;
    @ViewComponent
    private JmixButton removeSubjectButton;
    @ViewComponent
    private JmixButton addSubjectButton;
    @ViewComponent
    private HorizontalLayout buttonsPanel;
    @ViewComponent
    private DataGrid<SubjectStudent> registeredSubjectsDataGrid;


    @Subscribe
    public void onInit(final InitEvent event) {
        List<School> schools = (List<School>) schoolRepository.findAll();
        isSingleSchool = schools.size() == 1;
    }

    @Subscribe
    public void onInitEntity(final InitEntityEvent<Student> event) {
        addSubjectButton.setEnabled(false);
        buttonsPanel.setVisible(false);
        registeredSubjectsDataGrid.setVisible(false);
    }

    @Subscribe
    public void onReady(final ReadyEvent event) {
        initSubjects.addAll(getEditedEntity().getRegisteredSubjects().stream()
                .map(SubjectStudent::getId)
                .toList());
    }

    @Subscribe(id = "addSubjectButton", subject = "clickListener")
    public void onAddSubjectButtonClick(final ClickEvent<JmixButton> event) {
        DialogWindow<SubjectListView> dialogWindow = dialogWindows
                .lookup(this, Subject.class)
                .withViewClass(SubjectListView.class)
                .withLookupComponentMultiSelect(true)
                .withSelectHandler(selectedSubjects -> {
                    for (Subject selected : selectedSubjects) {
                        if (getEditedEntity().getRegisteredSubjects().stream()
                                .anyMatch(s -> s.getSubject().equals(selected))) {
                            continue;
                        }

                        SubjectStudent newSubject = dataManager.create(SubjectStudent.class);
                        newSubject.setStudent(getEditedEntity());
                        newSubject.setSubject(selected);

                        registeredSubjectsDc.getMutableItems().add(newSubject);
                    }
                }).build();

        SubjectListView subjectListView = dialogWindow.getView();

        if (!isSingleSchool) {
            subjectListView.setSchool(getEditedEntity().getClazz().getSchool());
        }

        dialogWindow.open();
    }

    @Subscribe("clazzField")
    public void onClazzFieldComponentValueChange(
            final AbstractField.ComponentValueChangeEvent<EntityComboBox<Clazz>, Clazz> event) {
        addSubjectButton.setEnabled(event.getValue() != null);

        if (event.getOldValue() != null && !event.getOldValue().getSchool().equals(event.getValue().getSchool())) {
            registeredSubjectsDc.getMutableItems().clear();
        }
    }

    @Subscribe(id = "registeredSubjectsDc", target = Target.DATA_CONTAINER)
    public void onRegisteredSubjectsDcItemChange(final InstanceContainer.ItemChangeEvent<SchoolSubject> event) {
        removeSubjectButton.setEnabled(event.getItem() != null);
    }

    @Subscribe(id = "removeSubjectButton", subject = "clickListener")
    public void onRemoveSubjectButtonClick(final ClickEvent<JmixButton> event) {
        dialogs.createOptionDialog()
                .withHeader("Remove subject")
                .withText("Are you sure you want to remove the subject?")
                .withActions(
                        new DialogAction(DialogAction.Type.YES).withHandler(actionPerformedEvent -> {
                            SubjectStudent targetItem = registeredSubjectsDc.getItem();

                            if (initSubjects.contains(targetItem.getId())) {
                                removedSubjects.add(targetItem);
                            }

                            registeredSubjectsDc.getMutableItems().remove(targetItem);
                        }),
                        new DialogAction(DialogAction.Type.NO).withVariant(ActionVariant.PRIMARY)
                ).open();
    }

    @Subscribe(target = Target.DATA_CONTEXT)
    public void onPreSave(final DataContext.PreSaveEvent event) {
        // TODO: fix optimistic lock popup when delete after change DURATION
        removedSubjects.forEach(dataManager::remove);
    }
}
