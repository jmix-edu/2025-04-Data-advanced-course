package com.company.projectmanagementdata.view.timeentry;

import com.company.projectmanagementdata.entity.TimeEntry;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

@Route(value = "time-entries/:id", layout = DefaultMainViewParent.class)
@ViewController(id = "TimeEntry.detail")
@ViewDescriptor(path = "time-entry-detail-view.xml")
@EditedEntityContainer("timeEntryDc")
public class TimeEntryDetailView extends StandardDetailView<TimeEntry> {
}