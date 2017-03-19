package cn.chenhaonee.hostelWorld.service;

import cn.chenhaonee.hostelWorld.dao.InnRepository;
import cn.chenhaonee.hostelWorld.dao.OrderRepository;
import cn.chenhaonee.hostelWorld.domain.InnForClient;
import cn.chenhaonee.hostelWorld.domain.RoomForClient;
import cn.chenhaonee.hostelWorld.model.Inn.Room;
import cn.chenhaonee.hostelWorld.model.common.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by nichenhao on 2017/3/20.
 */
@Service
public class InnService {

    @Autowired
    private InnRepository innRepository;

    @Autowired
    private OrderRepository orderRepository;

    public List<InnForClient> getInnList() {
        List<InnForClient> inns = innRepository
                .findAll()
                .stream()
                .map(inn -> new InnForClient(inn.getId(), inn.getNameForInn()))
                .collect(Collectors.toList());
        return inns;
    }

    public List<RoomForClient> getRoomList(String id, String start, String end) {
        List<RoomForClient> rooms = innRepository.findOne(id).getRooms()
                .stream()
                .map(room -> new RoomForClient(room.getId(), room.getRoomName(), room.getRoomType(), 0.0))
                .collect(Collectors.toList());

        String[] startDate = start.split("-");
        int startYear = Integer.parseInt(startDate[0]);
        int startMonth = Integer.parseInt(startDate[1]);
        int startDay = Integer.parseInt(startDate[2]);

        String[] endDate = end.split("-");
        int endYear = Integer.parseInt(endDate[0]);
        int endMonth = Integer.parseInt(endDate[1]);
        int endDay = Integer.parseInt(endDate[2]);
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.set(startYear, startMonth, startDay);
        Date from = startCalendar.getTime();
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.set(endYear, endMonth, endDay);
        Date to=endCalendar.getTime();

        List<Order> orders =


        return rooms;
    }


}
