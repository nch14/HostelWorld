package cn.chenhaonee.hostelWorld.repository;

import cn.chenhaonee.hostelWorld.model.common.Price;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by nichenhao on 2017/3/21.
 */
public interface PriceRepository extends JpaRepository<Price,Long>{


    Price findByInnOwnerNameAndRoomType(String name,String type);
}
