package com.company.projectmanagementdata.view.user;

import com.company.projectmanagementdata.app.UsersService;
import com.company.projectmanagementdata.entity.Project;
import com.company.projectmanagementdata.entity.User;
import com.company.projectmanagementdata.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.core.DataManager;
import io.jmix.core.LoadContext;
import io.jmix.flowui.component.genericfilter.GenericFilter;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.model.CollectionContainer;
import io.jmix.flowui.view.*;
import org.eclipse.persistence.internal.oxm.schema.model.All;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Route(value = "users", layout = MainView.class)
@ViewController(id = "User.list")
@ViewDescriptor(path = "user-list-view.xml")
@LookupComponent("usersDataGrid")
@DialogMode(width = "64em")
public class UserListView extends StandardListView<User> {

    @Autowired
    private UsersService usersService;
    @Autowired
    private DataManager dataManager;

    @ViewComponent
    private GenericFilter genericFilter;

    private Project filterProject;
    @ViewComponent
    private CollectionContainer<User> usersDc;
    @ViewComponent
    private DataGrid<User> usersDataGrid;

    @Install(to = "usersDl", target = Target.DATA_LOADER)
    private List<User> usersDlLoadDelegate(LoadContext<User> loadContext) {

        if (filterProject != null) {
            List<User> users = dataManager.loadList(loadContext);
            List<User> currentParticipants = filterProject.getParticipants();
            users.removeAll(currentParticipants);
            return users;
        }

//        // Searching in the database
//        LoadContext.Query query = loadContext.getQuery();
//        if (filterProject != null && query != null) {
//            return usersService.getUsersNotInProject(filterProject, query.getFirstResult(), query.getMaxResults());
//        }
        return dataManager.loadList(loadContext);
    }

    public void setFilterProject(Project project) {
        this.filterProject = project;
        genericFilter.setVisible(false);
    }
}