package com.ejemplo2.ejemplo2.service.impls;


import com.ejemplo2.ejemplo2.dao.api.UsersDaoApi;
import com.ejemplo2.ejemplo2.generics.GenericServiceImpl;
import com.ejemplo2.ejemplo2.models.User;
import com.ejemplo2.ejemplo2.service.api.UsersServiceApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;


@Service
public class UsersServiceImpl extends GenericServiceImpl<User,Long> implements UsersServiceApi {
    @Autowired
    private UsersDaoApi usersDaoApi;


    @Override
    public CrudRepository<User, Long> getDao() {
        return usersDaoApi;
    }
}
