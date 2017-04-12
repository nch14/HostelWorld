package cn.chenhaonee.hostelWorld.service;

import cn.chenhaonee.hostelWorld.repository.RoomRepository;
import cn.chenhaonee.hostelWorld.model.Inn.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by nichenhao on 2017/3/20.
 */
@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    public Room getRoom(Long id) {
        return roomRepository.findOne(id);
    }
}
