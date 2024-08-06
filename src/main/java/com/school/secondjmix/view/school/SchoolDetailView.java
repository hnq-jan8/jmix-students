package com.school.secondjmix.view.school;

import com.school.secondjmix.entity.School;
import com.school.secondjmix.entity.SchoolSubject;
import com.school.secondjmix.entity.Subject;
import com.school.secondjmix.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;
import io.jmix.core.DataManager;
import io.jmix.flowui.DialogWindows;
import io.jmix.flowui.Dialogs;
import io.jmix.flowui.action.DialogAction;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.kit.action.ActionVariant;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.model.CollectionPropertyContainer;
import io.jmix.flowui.model.DataContext;
import io.jmix.flowui.model.InstanceContainer;
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

@Route(value = "schools/:id", layout = MainView.class)
@ViewController("School.detail")
@ViewDescriptor("school-detail-view.xml")
@EditedEntityContainer("schoolDc")
public class SchoolDetailView extends StandardDetailView<School> {
    private final List<SchoolSubject> initSubjects = new ArrayList<>();
    private final List<SchoolSubject> removedSubjects = new ArrayList<>();

    @Autowired
    private DataManager dataManager;
    @Autowired
    private DialogWindows dialogWindows;
    @Autowired
    private Dialogs dialogs;

    @ViewComponent
    private CollectionPropertyContainer<SchoolSubject> subjectsDc;
    @ViewComponent
    private JmixButton removeSubjectButton;
    @ViewComponent
    private HorizontalLayout buttonsPanel;
    @ViewComponent
    private DataGrid<SchoolSubject> subjectsDataGrid;


    @Subscribe
    public void onReady(final ReadyEvent event) {
        initSubjects.addAll(getEditedEntity().getSubjects());
    }

    @Subscribe
    public void onInitEntity(final InitEntityEvent<School> event) {
        buttonsPanel.setVisible(false);
        subjectsDataGrid.setVisible(false);
    }

    @Subscribe(id = "addSubjectButton", subject = "clickListener")
    public void onAddSubjectButtonClick(final ClickEvent<JmixButton> event) {
        dialogWindows.lookup(this, Subject.class)
                .withLookupComponentMultiSelect(true)
                .withSelectHandler(selectedSubjects -> {
                    for (Subject selected : selectedSubjects) {
                        if (getEditedEntity().getSubjects().stream()
                                .anyMatch(s -> s.getSubject().equals(selected))) {
                            continue;
                        }

                        SchoolSubject newSubject = dataManager.create(SchoolSubject.class);
                        newSubject.setSchool(getEditedEntity());
                        newSubject.setSubject(selected);

                        subjectsDc.getMutableItems().add(newSubject);
                    }
                })
                .open();
    }

    @Subscribe(id = "subjectsDc", target = Target.DATA_CONTAINER)
    public void onSubjectsDcItemChange(final InstanceContainer.ItemChangeEvent<SchoolSubject> event) {
        removeSubjectButton.setEnabled(event.getItem() != null);
    }

    @Subscribe(id = "removeSubjectButton", subject = "clickListener")
    public void onRemoveSubjectButtonClick(final ClickEvent<JmixButton> event) {
        dialogs.createOptionDialog()
                .withHeader("Remove subject")
                .withText("Are you sure you want to remove the subject?")
                .withActions(new DialogAction(DialogAction.Type.YES).withHandler(actionPerformedEvent -> {
                            SchoolSubject subject = subjectsDc.getItem();
                            if (initSubjects.contains(subject)) {
                                removedSubjects.add(subject);
                            }

                            subjectsDc.getMutableItems().remove(subject);
                        }),
                        new DialogAction(DialogAction.Type.NO).withVariant(ActionVariant.PRIMARY)
                ).open();
    }

    @Subscribe(target = Target.DATA_CONTEXT)
    public void onPostSave(final DataContext.PostSaveEvent event) {
        removedSubjects.forEach(dataManager::remove);
    }
}
