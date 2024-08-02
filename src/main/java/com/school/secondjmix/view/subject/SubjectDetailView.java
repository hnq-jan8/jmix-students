package com.school.secondjmix.view.subject;

import com.school.secondjmix.entity.School;
import com.school.secondjmix.entity.SchoolSubject;
import com.school.secondjmix.entity.Subject;
import com.school.secondjmix.repository.SchoolRepository;
import com.school.secondjmix.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.core.DataManager;
import io.jmix.flowui.model.DataContext;
import io.jmix.flowui.view.EditedEntityContainer;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.Subscribe;
import io.jmix.flowui.view.Target;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Route(value = "subjects/:id", layout = MainView.class)
@ViewController("Subject.detail")
@ViewDescriptor("subject-detail-view.xml")
@EditedEntityContainer("subjectDc")
public class SubjectDetailView extends StandardDetailView<Subject> {
    @Autowired
    private SchoolRepository schoolRepository;
    @Autowired
    private DataManager dataManager;

    @Subscribe(target = Target.DATA_CONTEXT)
    public void onPostSave(final DataContext.PostSaveEvent event) {
        List<School> schools = (List<School>) schoolRepository.findAll();
        if (schools.size() != 1) return;

        Subject subject = getEditedEntity();
        School school = schools.getFirst();

        SchoolSubject ss = dataManager.create(SchoolSubject.class);
        ss.setSchool(school);
        ss.setSubject(subject);

        dataManager.save(ss);
    }
}