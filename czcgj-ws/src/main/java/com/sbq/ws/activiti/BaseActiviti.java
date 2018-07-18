package com.sbq.ws.activiti;

import org.activiti.engine.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by zhangyuan on 2017/3/7.
 */
public class BaseActiviti {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected ProcessEngine processEngine;

    @Autowired
    protected RepositoryService repositoryService;

    @Autowired
    protected RuntimeService runtimeService;

    @Autowired
    protected TaskService taskService;

    @Autowired
    protected HistoryService historyService;

    @Autowired
    protected IdentityService identityService;

    @Autowired
    protected ManagementService managementService;

    @Autowired
    protected FormService formService;

}
