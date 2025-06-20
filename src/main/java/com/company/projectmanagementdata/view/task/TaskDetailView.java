package com.company.projectmanagementdata.view.task;

import com.company.projectmanagementdata.entity.Task;
import com.company.projectmanagementdata.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.EditedEntityContainer;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "tasks/:id", layout = MainView.class)
@ViewController(id = "Task_.detail")
@ViewDescriptor(path = "task-detail-view.xml")
@EditedEntityContainer("taskDc")
public class TaskDetailView extends StandardDetailView<Task> {
}