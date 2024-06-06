package com.example.buensaboruno.servicesImpl;

import com.example.buensaboruno.domain.entities.Base;
import com.example.buensaboruno.repositories.BaseRepository;
import com.example.buensaboruno.services.BaseService;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;


public abstract class BaseServiceImpl <T extends Base, ID extends Serializable> implements BaseService<T, ID> {

    protected final BaseRepository<T, ID> baseRepository;

    public BaseServiceImpl(BaseRepository<T, ID> baseRepository) {
        this.baseRepository = baseRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> findAll() throws Exception {
        try {
            return baseRepository.findAll();
        } catch (Exception e) {
            throw new Exception("Error al encontrar todas las entidades: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public T findById(ID id) throws Exception {
        try {
            return baseRepository.findById(id).orElseThrow(() -> new Exception("Entidad no encontrada con ID: " + id));
        } catch (Exception e) {
            throw new Exception("Error al encontrar la entidad por ID: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public T save(T entity) throws Exception {
        try {
            return baseRepository.save(entity);
        } catch (Exception e) {
            throw new Exception("Error al guardar la entidad: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public T update(T entity) throws Exception {
        try {
            if (entity.getId() == null) {
                throw new Exception("La entidad a modificar debe contener un ID.");
            }
            return baseRepository.save(entity);
        } catch (Exception e) {
            throw new Exception("Error al actualizar la entidad: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public boolean delete(ID id) throws Exception {
        try {
            T entity = baseRepository.findById(id).orElseThrow(() -> new Exception("Entidad no encontrada con ID: " + id));
            baseRepository.delete(entity);
            return true;
        } catch (Exception e) {
            throw new Exception("Error al eliminar la entidad: " + e.getMessage(), e);
        }
    }

}
