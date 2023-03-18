package com.ejemplo2.ejemplo2.controller;

import com.ejemplo2.ejemplo2.models.User;
import com.ejemplo2.ejemplo2.service.api.UsersServiceApi;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@RestController
public class usersController {

    @Autowired
    private UsersServiceApi usersServiceApi;

    public static ResponseEntity<Object> generatedResponse(HttpStatus status, String messages, Object response) {
        HashMap<String, Object> res = new HashMap<>();
        res.put("data", response);
        res.put("messages", messages);
        res.put("status", status.value());
        return new ResponseEntity<Object>(res, status);
    }

    /*LISTAR TODOS LOS USUARIOS*/
    @RequestMapping(value = "api/users", method = RequestMethod.GET)
    public ResponseEntity<Object> getAll() {
        List<User> obj = usersServiceApi.getAll();
        return generatedResponse(HttpStatus.OK, "users", obj);
    }

    public boolean validarNumero(String cadena) {
        try {
            boolean variable = Boolean.parseBoolean(cadena);
            System.out.println(variable);
            return true;
        } catch (NumberFormatException nfe) {
            System.out.println(nfe);
            return false;
        }
    }

//    public boolean validarNumero(String cadena) {
//        try {
//            int numero = Integer.parseInt(cadena);
//            System.out.println(numero);
//            return true;
//        } catch (NumberFormatException nfe) {
//            System.out.println(nfe);
//            return false;
//        }
//    }
    /*METODO AGREGAR*/
    @RequestMapping(value = "api/registrar", method = RequestMethod.POST)
    public ResponseEntity<Object> save(@RequestBody User users) {
        if (!users.getName().isEmpty()) {
            try {
                //Valida que el campo seatipo String
                String tipoDato = users.getName().getClass().getSimpleName();

                System.out.println(tipoDato);
//                Integer s = Integer.parseInt(tipoDato);
//                System.out.println(s);
                if (tipoDato.equals("String")) {
                    Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
                    String pass = argon2.hash(1, 1024, 1, users.getBirthdate());
                    users.setPassword(pass);
                    Date create_at = new Date();
                    users.setCreated_at(create_at);
                    //User obj = usersServiceApi.save(users);
                    return generatedResponse(HttpStatus.OK, "user created", users);
                } else {
                    return generatedResponse(HttpStatus.UNAUTHORIZED, "Tipo de dato no es el correcto " + users.getName(), null);
                }

            } catch (NumberFormatException ex) {
                return generatedResponse(HttpStatus.UNAUTHORIZED, "Tipo de dato no es el correcto " + users.getName(), null);
            }

        } else {
            return generatedResponse(HttpStatus.UNAUTHORIZED, "El nombre es requerido", null);
        }
    }


    /*METODO ACTUALIZAR*/
    @RequestMapping(value = "api/actualizar/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody User up_users) {
        User users = usersServiceApi.get(id);
        Date tmp_created_at = users.getCreated_at();
        Date updated_at = new Date();

        System.out.println(up_users.getBirthdate());
        if(up_users.getName() == null){
            System.out.println("es null");
        }

        if (Objects.equals(id, up_users.getId())) {
            try {
                System.out.println(up_users.getMiddle_name());
                if (!up_users.getMiddle_name().isEmpty()) {

                    up_users.setUpdated_at(updated_at);
                    up_users.setCreated_at(tmp_created_at);

                    Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
                    String pass = argon2.hash(1, 1024, 1, up_users.getBirthdate());
                    up_users.setPassword(pass);

                    User obj = usersServiceApi.save(up_users);
                    //return new ResponseEntity<Users>(obj, HttpStatus.OK);
                    return generatedResponse(HttpStatus.OK, "user updated", obj);


                }

                System.out.println(up_users.getName());

                if(up_users.getName() == null){
                    return generatedResponse(HttpStatus.BAD_REQUEST, "El campo es requerido nombre no viene el payload", null);

                }
                else if (!up_users.getLast_name().isEmpty()) {

                    up_users.setUpdated_at(updated_at);
                    up_users.setCreated_at(tmp_created_at);

                    Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
                    String pass = argon2.hash(1, 1024, 1, up_users.getBirthdate());
                    up_users.setPassword(pass);

                    User obj = usersServiceApi.save(up_users);

                    return generatedResponse(HttpStatus.OK, "user updated", obj);

                } else {
                    return generatedResponse(HttpStatus.BAD_REQUEST, "El campo es requerido", null);

                }

            } catch (NullPointerException ex) {
                return generatedResponse(HttpStatus.BAD_REQUEST, "El payload esta incompleto", null);
                //System.out.println(up_users.getLast_name());
            }catch (DataIntegrityViolationException ex){
                return generatedResponse(HttpStatus.BAD_REQUEST, "El payload esta incompleto", null);
            }
        } else {
            //return new ResponseEntity<Users>(users, HttpStatus.INTERNAL_SERVER_ERROR);
            return generatedResponse(HttpStatus.BAD_REQUEST, "not updated, id no corresponde al usuario", users);
        }
    }

    /*METODO ELIMINAR*/
    @RequestMapping(value = "api/user/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        User users = usersServiceApi.get(id);
        if (users != null) {
            usersServiceApi.delete(id);
        } else {
            //return new ResponseEntity<Users>(users,HttpStatus.INTERNAL_SERVER_ERROR);
            return generatedResponse(HttpStatus.INTERNAL_SERVER_ERROR, "user not eliminated", users);
        }
        //return new ResponseEntity<Users>(users,HttpStatus.OK);
        return generatedResponse(HttpStatus.OK, "user eliminated", users);
    }

    /*METODO BUSCAR*/
    @RequestMapping(value = "api/user/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> find(@PathVariable Long id) {
        User users = usersServiceApi.get(id);
        //return new ResponseEntity<Users>(users,HttpStatus.OK);
        return generatedResponse(HttpStatus.OK, "user find", users);
    }

}
