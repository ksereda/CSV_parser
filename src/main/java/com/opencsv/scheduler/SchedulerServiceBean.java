package com.opencsv.scheduler;

import com.opencsv.facade.SchedulerService;
import com.opencsv.facade.ServiceABCService;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Singleton
public class SchedulerServiceBean implements SchedulerService {

    @Inject
    private ServiceABCService serviceABCService;

    @Override
    public void schedulerJebbitTask() {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                serviceABCService.lookUpFiles();
            }
        }, 5, 5, TimeUnit.MINUTES);
    }
}
