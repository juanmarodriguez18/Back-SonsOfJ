package com.example.buensaboruno.controllers;

import com.example.buensaboruno.domain.entities.Base;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.Serializable;

public interface BaseController<T extends Base,ID extends Serializable> {

    public ResponseEntity<?> getAll();
    public ResponseEntity<?> getById(@PathVariable ID id);
    public ResponseEntity<?> save(@RequestBody T t);
    public ResponseEntity<?> update(@PathVariable ID id,@RequestBody T t);
    public ResponseEntity<?> delete(@PathVariable ID id);  // el ? es un comodin para recibir cualquier tipo de Objeto
    // otra opcion seria T extends Object

}
