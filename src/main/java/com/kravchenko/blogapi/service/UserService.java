package com.kravchenko.blogapi.service;

import com.kravchenko.blogapi.model.User;
import com.kravchenko.blogapi.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService extends AbstractService<User, String, UserRepository> {

}
