package com.opencsv.facade;

import com.opencsv.ServiceABC.entity.ServiceABCFileLineEntity;
import com.opencsv.ServiceABC.parser.ExtendedServiceABCFileLine;
import com.opencsv.services.ServiceABCServiceImpl;

import java.io.File;

@ImplementedBy(ServiceABCServiceImpl.class)
public interface ServiceABCService {

    void lookUpFiles();

    void saveToDb(ServiceABCFileLineEntity entity);

    ExtendedServiceABCFileLine createExtendedServiceABCFileLine(ServiceABCFileLineEntity entity);

    void moveFileToSuccessDirectory(File file);

    void moveFileToFailDirectory(File file);

}
