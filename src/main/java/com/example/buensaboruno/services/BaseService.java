package com.example.buensaboruno.services;

import java.io.Serializable;
import java.util.List;

import com.example.buensaboruno.domain.entities.Base;

public interface BaseService<T extends Base, ID extends Serializable> {

    List<T> findAll() throws Exception;
    T findById(ID id) throws Exception;
    T save(T entity) throws Exception;
    T update(T entity) throws Exception;
    boolean delete(ID id) throws Exception;

}
