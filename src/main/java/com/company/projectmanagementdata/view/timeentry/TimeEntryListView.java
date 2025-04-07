package com.company.projectmanagementdata.view.timeentry;

import com.company.projectmanagementdata.entity.TimeEntry;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;


@Route(value = "time-entries", layout = DefaultMainViewParent.class)
@ViewController(id = "TimeEntry.list")
@ViewDescriptor(path = "time-entry-list-view.xml")
@LookupComponent("timeEntriesDataGrid")
@DialogMode(width = "64em")
public class TimeEntryListView extends StandardListView<TimeEntry> {
}