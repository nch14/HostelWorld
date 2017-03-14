package cn.chenhaonee.hostelWorld.dao;

import cn.chenhaonee.hostelWorld.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by nichenhao on 2017/3/13.
 */
@Repository
public interface UserRepository extends JpaRepository<User,Long>{
    Optional<User> findByUsername(String username);
}
