package com.company.projectmanagementdata.view.workload;


import com.company.projectmanagementdata.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "workload-view", layout = MainView.class)
@ViewController(id = "WorkloadView")
@ViewDescriptor(path = "workload-view.xml")
public class WorkloadView extends StandardView {
}