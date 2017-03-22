package cn.chenhaonee.hostelWorld.dao;

import cn.chenhaonee.hostelWorld.model.common.RoomArrangement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

/**
 * Created by nichenhao on 2017/3/21.
 */
public interface RoomArragementDao extends JpaRepository<RoomArrangement, Long> {

    RoomArrangement findByRoomIdAndInnNameAndDate(Long roomId, String inn, Date date);

    List<RoomArrangement> findByInnNameAndDate(String inn, Date date);
}
