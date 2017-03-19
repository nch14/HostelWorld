package cn.chenhaonee.hostelWorld.dao;

import cn.chenhaonee.hostelWorld.model.Inn.Inn;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by nichenhao on 2017/3/20.
 */
public interface InnRepository extends JpaRepository<Inn,String> {
}
