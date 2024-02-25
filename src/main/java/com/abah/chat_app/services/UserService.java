package com.abah.chat_app.services;

import com.abah.chat_app.exceptions.DuplicateException;
import com.abah.chat_app.exceptions.UserNotFoundException;
import com.abah.chat_app.model.User;

import java.util.List;

public interface UserService {
    List<User> getAll() throws UserNotFoundException;

    User addUser(User user) throws DuplicateException;

    User getUserByUserName(String username)  throws UserNotFoundException;
}
