package com.opencsv.dao;

import com.opencsv.ServiceABC.entity.ServiceABCFileLineEntity;
import com.opencsv.data.ServiceABCFileLineDaoBean;

import java.util.List;

@ImplementedBy(ServiceABCFileLineDaoBean.class)
public interface ServiceABCFileLineDao {

    void persist(ServiceABCFileLineEntity stateEntity);

    void persistAll(List<ServiceABCFileLineEntity> jebbitFileLineEntity);

}
