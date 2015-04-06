package com.cmpe281.team2.miaas.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.cmpe281.team2.miaas.core.mysql.DataAccess;

@Repository
public class GenericDAO<T extends java.io.Serializable> {
    @Autowired
    @Qualifier("miaasDataAccess")
    protected DataAccess dataAccess;
    
    public final void setDataAccess(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }
}
