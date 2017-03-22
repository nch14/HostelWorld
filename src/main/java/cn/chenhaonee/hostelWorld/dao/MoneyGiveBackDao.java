package cn.chenhaonee.hostelWorld.dao;

import cn.chenhaonee.hostelWorld.model.MoneyGiveBack;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by nichenhao on 2017/3/22.
 */
public interface MoneyGiveBackDao extends JpaRepository<MoneyGiveBack,Long> {

    MoneyGiveBack findByInnName(String innName);
}
