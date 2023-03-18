package com.ejemplo2.ejemplo2.generics;

import java.io.Serializable;
import java.util.List;

public interface GenericServiceApi <T,ID extends Serializable>{

    T save(T entity);

    void delete (ID id);

    T get (ID id);

    List<T> getAll();

}