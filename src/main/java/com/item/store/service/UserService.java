package com.item.store.service;

import com.item.store.dto.UserDto;
import com.item.store.entity.User;
import com.item.store.mapper.UserMapper;
import com.item.store.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    public void saveUser(UserDto userDto){
        User user = userMapper.map(userDto);
        userRepository.save(user);
    }
}
