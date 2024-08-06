package com.school.secondjmix.view.block;

import com.school.secondjmix.entity.Block;
import com.school.secondjmix.entity.BlockSubject;
import com.school.secondjmix.entity.Subject;
import com.school.secondjmix.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;
import io.jmix.core.DataManager;
import io.jmix.core.Id;
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
import java.util.UUID;


@Route(value = "blocks/:id", layout = MainView.class)
@ViewController("Block.detail")
@ViewDescriptor("block-detail-view.xml")
@EditedEntityContainer("blockDc")
public class BlockDetailView extends StandardDetailView<Block> {
    private final List<UUID> initSubjects = new ArrayList<>();
    private final List<UUID> removedSubjects = new ArrayList<>();
    private boolean isCreate = false;

    @Autowired
    private DialogWindows dialogWindows;
    @Autowired
    private DataManager dataManager;

    @ViewComponent
    private CollectionPropertyContainer<BlockSubject> subjectsDc;
    @ViewComponent
    private JmixButton removeSubjectButton;
    @Autowired
    private Dialogs dialogs;
    @ViewComponent
    private HorizontalLayout buttonsPanel;
    @ViewComponent
    private DataGrid<BlockSubject> subjectsDataGrid;


    @Subscribe
    public void onInitEntity(final InitEntityEvent<Block> event) {
        isCreate = true;
        buttonsPanel.setVisible(false);
        subjectsDataGrid.setVisible(false);
    }

    @Subscribe
    public void onReady(final ReadyEvent event) {
        if (isCreate) return;
        initSubjects.addAll(getEditedEntity().getSubjects().stream()
                .map(BlockSubject::getId)
                .toList());
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

                        BlockSubject newSubject = dataManager.create(BlockSubject.class);
                        newSubject.setBlock(getEditedEntity());
                        newSubject.setSubject(selected);

                        subjectsDc.getMutableItems().add(newSubject);
                    }
                }).open();
    }

    @Subscribe(id = "subjectsDc", target = Target.DATA_CONTAINER)
    public void onSubjectsDcItemChange(final InstanceContainer.ItemChangeEvent<BlockSubject> event) {
        removeSubjectButton.setEnabled(event.getItem() != null);
    }

    @Subscribe(id = "removeSubjectButton", subject = "clickListener")
    public void onRemoveSubjectButtonClick(final ClickEvent<JmixButton> event) {
        dialogs.createOptionDialog()
                .withHeader("Remove subject")
                .withText("Are you sure you want to remove the subject?")
                .withActions(
                        new DialogAction(DialogAction.Type.YES).withHandler(actionPerformedEvent -> {
                            BlockSubject targetItem = subjectsDc.getItem();
                            subjectsDc.getMutableItems().remove(targetItem);

                            if (initSubjects.contains(targetItem.getId())) {
                                removedSubjects.add(targetItem.getId());
                            }
                        }),
                        new DialogAction(DialogAction.Type.NO).withVariant(ActionVariant.PRIMARY)
                ).open();
    }

    @Subscribe(target = Target.DATA_CONTEXT)
    public void onPostSave(final DataContext.PostSaveEvent event) {
        removedSubjects.forEach(id -> dataManager.remove(Id.of(id, BlockSubject.class)));
    }
}