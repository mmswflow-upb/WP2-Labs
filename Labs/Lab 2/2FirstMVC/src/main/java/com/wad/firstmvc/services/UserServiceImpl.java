package com.wad.firstmvc.services;

import com.wad.firstmvc.domain.User;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {
    private List<User> users = new ArrayList<>();

    @Override
    public List<User> findAll() {
        return users;
    }

    @Override
    public void save(User u) {
        if(u.getId() == null) {
            u.setId(new Random().nextLong());
        }
        users.add(u);
    }
}
