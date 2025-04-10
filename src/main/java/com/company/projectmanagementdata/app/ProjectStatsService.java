package com.company.projectmanagementdata.app;

import com.company.projectmanagementdata.entity.Project;
import com.company.projectmanagementdata.entity.ProjectStats;
import com.company.projectmanagementdata.entity.Task;
import com.company.projectmanagementdata.entity.User;
import io.jmix.core.DataManager;
import io.jmix.core.FetchPlan;
import io.jmix.core.FetchPlanRepository;
import io.jmix.core.FetchPlans;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class ProjectStatsService {
    private final DataManager dataManager;
    private final FetchPlans fetchPlans;
    private final FetchPlanRepository fetchPlanRepository;

    public ProjectStatsService(DataManager dataManager, FetchPlans fetchPlans, FetchPlanRepository fetchPlanRepository) {
        this.dataManager = dataManager;
        this.fetchPlans = fetchPlans;
        this.fetchPlanRepository = fetchPlanRepository;
    }

    public List<ProjectStats> fetchStats() {

        List<Project> projects = dataManager.load(Project.class)
                .all()
                .fetchPlan(createFetchPlanWithTasks())
                .list();

        return projects.stream()
                .map(project -> {
                    ProjectStats stats = dataManager.create(ProjectStats.class);
                    stats.setProjectName(project.getName());

                    List<Task> tasks = project.getTasks();
                    stats.setTasksCount(tasks.size());

                    for (Task tsk : tasks) {
                        User assignee = tsk.getAssignee();
                        System.out.println(assignee.getDisplayName());
                    }

                    Integer estimatedEfforts = tasks.stream()
                            .map(task -> task.getEstimatedEfforts() == null
                                    ? 0
                                    : task.getEstimatedEfforts())
                            .reduce(0, Integer::sum);

                    stats.setPlannedEfforts(estimatedEfforts);
                    stats.setActualEfforts(getActualEfforts(project.getId()));
                   return stats;

                })
                .toList();
    }

    private Integer getActualEfforts(UUID id) {
        return dataManager.loadValue("select sum(te.timeSpent) from TimeEntry te " +
                        "where te.task.project.id = :projectId", Integer.class)
                .parameter("projectId", id)
                .one();
    }

    private FetchPlan createFetchPlanWithTasks() {
        return fetchPlans.builder(Project.class)
                .addFetchPlan(FetchPlan.INSTANCE_NAME)
                .add("tasks",tasksFetchPlanBuilder ->
                        tasksFetchPlanBuilder.add("estimatedEfforts")
                                        .add("assignee", userFetchPlanBuilder ->
                                                userFetchPlanBuilder.addFetchPlan(FetchPlan.INSTANCE_NAME)))
                .build();
    }
}