package com.ejemplo2.ejemplo2.generics;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public abstract class GenericServiceImpl<T,ID extends Serializable> implements GenericServiceApi<T,ID> {

    @Override
    public T save(T entity) {
        return getDao().save(entity);
    }

    @Override
    public void delete(ID id) {
        getDao().deleteById(id);
    }

    @Override
    public T get(ID id) {
        Optional<T> obj = getDao().findById(id);
        if(obj.isPresent()){
            return obj.get();
        }
        return null;
    }

    @Override
    public List<T> getAll() {
        List<T> obj = new ArrayList<>();
        getDao().findAll().forEach(rs -> obj.add(rs));
        return obj;
    }


    public abstract CrudRepository<T,ID> getDao();
}
