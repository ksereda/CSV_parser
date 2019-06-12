package com.opencsv.data;

import com.opencsv.ServiceABC.entity.ServiceABCFileLineEntity;
import com.opencsv.dao.ServiceABCFileLineDao;

public class ServiceABCFileLineDaoBean extends ValidatableDaoBean<ServiceABCFileLineEntity, Long> implements ServiceABCFileLineDao {

    @Override
    protected Class<ServiceABCFileLineEntity> getEntityClass() {
        return ServiceABCFileLineEntity.class;
    }
}
