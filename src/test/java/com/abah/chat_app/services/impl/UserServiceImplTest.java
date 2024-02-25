package com.abah.chat_app.services.impl;

import com.abah.chat_app.exceptions.UserNotFoundException;
import com.abah.chat_app.model.User;
import com.abah.chat_app.repository.UserRepository;
import com.abah.chat_app.util.TestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Test
    void givenExistingUsers_whenGetAllUsers_thenReturnAllUsers() throws UserNotFoundException {
        List<User> mockedUsers = Collections.singletonList(TestData.buildUser());

        when(userRepository.findAll()).thenReturn(mockedUsers);

        List<User> users = userService.getAll();

        assertNotNull(users);
        assertArrayEquals(mockedUsers.toArray(), users.toArray());
        assertTrue(users.size() > 0);
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void givenEmptyUserList_whenGetAllUsers_thenThrowUserNotFoundException() {
        List<User> mockedUsers = Collections.EMPTY_LIST;

        when(userRepository.findAll()).thenReturn(mockedUsers);

        assertThrows(UserNotFoundException.class, () -> userService.getAll());
    }
}