package com.opencsv.facade;

import com.opencsv.scheduler.SchedulerServiceBean;

@ImplementedBy(SchedulerServiceBean.class)
public interface SchedulerService {

    void schedulerJebbitTask();

}
