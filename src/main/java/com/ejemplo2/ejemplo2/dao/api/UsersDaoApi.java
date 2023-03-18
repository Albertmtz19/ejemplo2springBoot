package com.ejemplo2.ejemplo2.dao.api;


import com.ejemplo2.ejemplo2.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UsersDaoApi extends CrudRepository<User,Long> {

}
