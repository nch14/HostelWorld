package cn.chenhaonee.hostelWorld.service;

import cn.chenhaonee.hostelWorld.dao.UserRepository;
import cn.chenhaonee.hostelWorld.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by nichenhao on 2017/3/19.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public boolean userIsExisting(String username) {
        Optional<User> userWrapper = userRepository.findByUsername(username);
        boolean result = userWrapper.isPresent();
        return result;
    }
}
