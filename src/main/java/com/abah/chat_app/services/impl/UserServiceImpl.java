package com.abah.chat_app.services.impl;

import com.abah.chat_app.exceptions.DuplicateException;
import com.abah.chat_app.exceptions.UserNotFoundException;
import com.abah.chat_app.model.User;
import com.abah.chat_app.repository.UserRepository;
import com.abah.chat_app.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<User> getAll() throws UserNotFoundException {
        List<User> users=userRepository.findAll();
        if (users.isEmpty()){
            throw new UserNotFoundException();
        }else {
           return users;
        }
    }

    @Override
    public User addUser(User user) throws DuplicateException {
       Optional<User> user1=userRepository.findById(user.getUserName());

       if (user1.isPresent()){
           throw new DuplicateException();
       }else {
           return userRepository.save(user);
       }
    }

    @Override
    public User getUserByUserName(String username) throws UserNotFoundException {
        Optional<User> user1=userRepository.findById(username);

        if (user1.isPresent()){
            return user1.get();
        }else {
            throw new UserNotFoundException();
        }
    }

}
