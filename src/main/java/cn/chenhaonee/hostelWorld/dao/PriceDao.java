package cn.chenhaonee.hostelWorld.dao;

import cn.chenhaonee.hostelWorld.model.common.Price;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by nichenhao on 2017/3/21.
 */
public interface PriceDao extends JpaRepository<Price,Long>{


    Price findByInnOwnerNameAndRoomType(String name,String type);
}
