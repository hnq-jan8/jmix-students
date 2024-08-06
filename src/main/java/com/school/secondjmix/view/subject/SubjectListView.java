package com.school.secondjmix.view.subject;

import com.school.secondjmix.entity.Block;
import com.school.secondjmix.entity.School;
import com.school.secondjmix.entity.SchoolSubject;
import com.school.secondjmix.entity.Subject;
import com.school.secondjmix.repository.SchoolRepository;
import com.school.secondjmix.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.router.Route;
import io.jmix.core.DataManager;
import io.jmix.flowui.Dialogs;
import io.jmix.flowui.action.DialogAction;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.model.CollectionContainer;
import io.jmix.flowui.model.CollectionLoader;
import io.jmix.flowui.model.InstanceContainer;
import io.jmix.flowui.view.DialogMode;
import io.jmix.flowui.view.LookupComponent;
import io.jmix.flowui.view.StandardListView;
import io.jmix.flowui.view.Subscribe;
import io.jmix.flowui.view.Target;
import io.jmix.flowui.view.ViewComponent;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@Route(value = "subjects", layout = MainView.class)
@ViewController("Subject.list")
@ViewDescriptor("subject-list-view.xml")
@LookupComponent("subjectsDataGrid")
@DialogMode(width = "64em")
public class SubjectListView extends StandardListView<Subject> {
    @Autowired
    private SchoolRepository schoolRepository;
    @Autowired
    private Dialogs dialogs;
    @Autowired
    private DataManager dataManager;

    @ViewComponent
    private CollectionLoader<Subject> subjectsDl;
    @ViewComponent
    private CollectionContainer<Subject> subjectsDc;
    @ViewComponent
    private JmixButton cusRemoveBtn;


    @Subscribe(id = "subjectsDc", target = Target.DATA_CONTAINER)
    public void onSubjectsDcItemChange(final InstanceContainer.ItemChangeEvent<Subject> event) {
        cusRemoveBtn.setEnabled(event.getItem() != null);
    }

    @Subscribe(id = "cusRemoveBtn", subject = "clickListener")
    public void onCusRemoveBtnClick(final ClickEvent<JmixButton> event) {
        dialogs.createOptionDialog()
                .withHeader("Confirmation")
                .withText("Are you sure you want to delete selected elements?")
                .withActions(
                        new DialogAction(DialogAction.Type.YES).withHandler(e -> {
                            List<School> schools = (List<School>) schoolRepository.findAll();
                            if (schools.size() == 1) {
                                SchoolSubject schoolSubject = dataManager.load(SchoolSubject.class)
                                        .query("""
                                                        select ss from SchoolSubject ss
                                                        where ss.subject = :subject
                                                        and ss.school = :school
                                                """)
                                        .parameter("subject", subjectsDc.getItem())
                                        .parameter("school", schools.getFirst())
                                        .one();

                                dataManager.remove(schoolSubject);
                            }

                            Subject subject = subjectsDc.getItem();
                            dataManager.remove(subject);
                            subjectsDl.load();
                        }),
                        new DialogAction(DialogAction.Type.NO)
                ).open();
    }

    public void setSchool(School school) {
        if (school != null) {
            String initQuery = subjectsDl.getQuery();
            String query = initQuery + (initQuery.contains("where e in") ? " and " : " where ")
                    + "e in (select s from SchoolSubject ss join ss.subject s where ss.school = :school)";

            subjectsDl.setQuery(query);
            subjectsDl.setParameter("school", school);

            subjectsDl.load();
        }
    }

    public void setBlock(Block block) {
        if (block != null) {
            String initQuery = subjectsDl.getQuery();
            String query = initQuery + (initQuery.contains("where e in") ? " and " : " where e in ")
                    + "e in (select s from BlockSubject bs join bs.subject s where bs.block = :block)";

            subjectsDl.setQuery(query);
            subjectsDl.setParameter("block", block);

            subjectsDl.load();
        }
    }
}