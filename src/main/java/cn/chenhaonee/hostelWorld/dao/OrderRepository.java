package cn.chenhaonee.hostelWorld.dao;

import cn.chenhaonee.hostelWorld.model.Inn.Inn;
import cn.chenhaonee.hostelWorld.model.common.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.OneToOne;
import java.util.Date;
import java.util.List;

/**
 * Created by nichenhao on 2017/3/20.
 */
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByInnAndArrivalDateBeforerAndLeaveDateAfter(Inn inn, Date from, Date to);

    List<Order> findByInnAndArrivalDateBeforeAndLeaveDateBefore(Inn inn, Date fromOne, Date fromTwo);

    List<Order> findByInnAndArrivalDateAfterAndLeaveDateAfter(Inn inn, Date toOne, Date toTwo);

}
