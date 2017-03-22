package cn.chenhaonee.hostelWorld.dao;

import cn.chenhaonee.hostelWorld.model.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by nichenhao on 2017/3/22.
 */
public interface ManagerDao extends JpaRepository<Manager,String> {
}
