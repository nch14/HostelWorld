package cn.chenhaonee.hostelWorld.dao;

import cn.chenhaonee.hostelWorld.model.Inn.Inn;
import cn.chenhaonee.hostelWorld.model.common.OrderBill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * Created by nichenhao on 2017/3/20.
 */
public interface OrderRepository extends JpaRepository<OrderBill, String> {
    List<OrderBill> findByInnAndArrivalDateBeforeAndLeaveDateAfter(String inn, Date to, Date from);

    @Query("select count(*) from OrderBill o where o.id = ?1")
    int findByCardNum(String id);

    List<OrderBill> findByUsernameOrderByMakeTimeDesc(String username);
}
