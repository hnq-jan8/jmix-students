package com.school.secondjmix.view.clazz;

import com.school.secondjmix.entity.Clazz;
import com.school.secondjmix.entity.School;
import com.school.secondjmix.entity.Student;
import com.school.secondjmix.repository.SchoolRepository;
import com.school.secondjmix.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.component.combobox.EntityComboBox;
import io.jmix.flowui.model.CollectionLoader;
import io.jmix.flowui.model.InstanceContainer;
import io.jmix.flowui.view.EditedEntityContainer;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.Subscribe;
import io.jmix.flowui.view.Target;
import io.jmix.flowui.view.ViewComponent;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@Route(value = "classes/:id", layout = MainView.class)
@ViewController("Clazz.detail")
@ViewDescriptor("clazz-detail-view.xml")
@EditedEntityContainer("clazzDc")
public class ClazzDetailView extends StandardDetailView<Clazz> {
    @Autowired
    private SchoolRepository schoolRepository;

    @ViewComponent
    private CollectionLoader<Student> studentsDl;

    @ViewComponent
    private EntityComboBox<School> schoolField;

    /**
     * The only school in the system, null if there are several schools.
     *
     * @see #singleSchoolFieldHandle()
     */
    private School theOnlySchool = null;

    @Subscribe
    public void onInit(final InitEvent event) {
        singleSchoolFieldHandle();
    }

    @Subscribe
    public void onInitEntity(final InitEntityEvent<Clazz> event) {
        if (theOnlySchool != null) {
            event.getEntity().setSchool(theOnlySchool);
        }
    }

    @Subscribe(id = "clazzDc", target = Target.DATA_CONTAINER)
    public void onClazzDcItemChange(final InstanceContainer.ItemChangeEvent<Clazz> event) {
        studentsDl.setParameter("class", event.getItem());
        studentsDl.load();
    }

    /**
     * If there is only one school in the system, the school field should be read-only
     * with {@link #theOnlySchool} selected.
     */
    private void singleSchoolFieldHandle() {
        List<School> schools = (List<School>) schoolRepository.findAll();
        boolean isSingleSchool = schools.size() == 1;

        theOnlySchool = isSingleSchool ? schools.getFirst() : null;
        schoolField.setReadOnly(isSingleSchool);
    }
}
