package com.company.projectmanagementdata.view.projectstats;


import com.company.projectmanagementdata.app.ProjectStatsService;
import com.company.projectmanagementdata.entity.ProjectStats;
import com.company.projectmanagementdata.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.core.LoadContext;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Route(value = "project-stats-view", layout = MainView.class)
@ViewController(id = "ProjectStatsView")
@ViewDescriptor(path = "project-stats-view.xml")
public class ProjectStatsView extends StandardView {

    @Autowired
    private ProjectStatsService projectStatsService;

    @Install(to = "projectStatsDl", target = Target.DATA_LOADER)
    private List<ProjectStats> projectStatsDlLoadDelegate(final LoadContext<ProjectStats> loadContext) {
        return projectStatsService.fetchStats();
    }
}