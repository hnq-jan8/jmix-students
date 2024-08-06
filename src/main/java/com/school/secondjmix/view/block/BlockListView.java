package com.school.secondjmix.view.block;

import com.school.secondjmix.entity.Block;
import com.school.secondjmix.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.DialogMode;
import io.jmix.flowui.view.LookupComponent;
import io.jmix.flowui.view.StandardListView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;


@Route(value = "blocks", layout = MainView.class)
@ViewController("Block.list")
@ViewDescriptor("block-list-view.xml")
@LookupComponent("blocksDataGrid")
@DialogMode(width = "64em")
public class BlockListView extends StandardListView<Block> {
}