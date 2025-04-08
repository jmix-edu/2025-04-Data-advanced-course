package com.company.projectmanagementdata.view.customsearch;


import com.company.projectmanagementdata.entity.Customer;
import com.company.projectmanagementdata.entity.Order;
import com.company.projectmanagementdata.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.model.CollectionContainer;
import io.jmix.flowui.view.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Route(value = "custom-search-view", layout = MainView.class)
@ViewController(id = "CustomSearchView")
@ViewDescriptor(path = "custom-search-view.xml")
public class CustomSearchView extends StandardView {

    private static final Logger log = LoggerFactory.getLogger(CustomSearchView.class);

    @ViewComponent
    private CollectionContainer<Customer> customersDc;
    @ViewComponent
    private CollectionContainer<Order> ordersDc;

    @Subscribe
    public void onInit(InitEvent event) {
        log.info("InitEvent - customers size: " + customersDc.getItems().size());
    }

    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        log.info("BeforeShowEvent - customers size: " + customersDc.getItems().size());
        log.info("BeforeShowEvent - orders size: " + ordersDc.getItems().size());
    }
    
    
}