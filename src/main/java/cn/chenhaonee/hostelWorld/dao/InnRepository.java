package cn.chenhaonee.hostelWorld.dao;

import cn.chenhaonee.hostelWorld.model.Inn.Inn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by nichenhao on 2017/3/20.
 */
public interface InnRepository extends JpaRepository<Inn,String> {
    @Query("select count(*) from Inn i where i.id = ?1")
    int findByInnNum(String id);
}
