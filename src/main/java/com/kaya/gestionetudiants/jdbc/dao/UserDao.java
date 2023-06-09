package com.kaya.gestionetudiants.jdbc.dao;

import com.kaya.gestionetudiants.jdbc.entities.User;

public interface UserDao {
    boolean auth(User user);
}
