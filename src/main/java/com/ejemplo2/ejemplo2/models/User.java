package com.ejemplo2.ejemplo2.models;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter @Getter @Column(name = "id")
    private Long id;

    @NotNull
    @Setter @Getter @Column(name = "name")
    private String name;

    @Setter @Getter @Column(name = "middle_name")
    private String middle_name;

    @Setter @Getter @Column(name = "last_name")
    private String last_name;

    @Setter @Getter @Column(name = "birthdate")
    private String birthdate;

    @Setter @Getter @Column(name = "phone")
    private String phone;

    @Setter @Getter @Column(name = "rfc")
    private String rfc;

    @Setter @Getter @Column(name = "policy")
    private String policy;

    @Setter @Getter @Column(name = "status")
    private boolean status;

    @Setter @Getter @Column(name = "email")
    private String email;

    @Setter @Getter @Column(name = "email_verified_at")
    private Date email_verified_at;

    @Setter @Getter @Column(name = "password")
    private String password;

    @Setter @Getter @Column(name = "remember_token")
    private String remember_token;

    @Setter @Getter @Column(name = "created_at")
    private Date created_at;

    @Setter @Getter @Column(name = "updated_at")
    private Date updated_at;
}
