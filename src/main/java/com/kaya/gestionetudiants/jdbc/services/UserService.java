package com.kaya.gestionetudiants.jdbc.services;

import com.kaya.gestionetudiants.jdbc.dao.Imp.UserDaoImpl;
import com.kaya.gestionetudiants.jdbc.entities.User;

public class UserService {
    UserDaoImpl userDao= new UserDaoImpl();

    public boolean auth(User user){
        return userDao.auth(user);
    }
}
