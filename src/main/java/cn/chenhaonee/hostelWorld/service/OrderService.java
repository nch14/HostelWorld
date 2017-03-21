package cn.chenhaonee.hostelWorld.service;

import cn.chenhaonee.hostelWorld.dao.OrderRepository;
import cn.chenhaonee.hostelWorld.model.Inn.Inn;
import cn.chenhaonee.hostelWorld.model.Inn.Room;
import cn.chenhaonee.hostelWorld.model.common.OrderBill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by nichenhao on 2017/3/20.
 */
@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RoomService roomService;

    public OrderBill findOne(String id) {
        return orderRepository.findOne(id);
    }

    public List<String> getUnavalibleRoomNames(String inn, Date start, Date end) {
        List<OrderBill> theseDaysOrderBills = orderRepository.findByInnAndArrivalDateBeforeAndLeaveDateAfter(inn, end, start);
        List<String> rooms = theseDaysOrderBills.stream()
                .map(orderBill -> orderBill.getRoom().getRoomName())
                .collect(Collectors.toList());
        return rooms;
    }

    public void makeAnOrder(String username, String hotelId, Long roomId, Date inDate, Date outDate) {
        // TODO: 2017/3/21 计算cost
        Room room = roomService.getRoom(roomId);
        OrderBill orderBill = new OrderBill(generateAnAvalibleId(), username, hotelId, room, inDate, outDate);
        orderBill.setState("已预订");
        orderRepository.save(orderBill);
    }

    public OrderBill makeAnOrder(String username, String hotelId, Long roomId, Date inDate, Date outDate,List<String> guest) {
        // TODO: 2017/3/21 计算cost
        Room room = roomService.getRoom(roomId);
        OrderBill orderBill = new OrderBill(generateAnAvalibleId(), username, hotelId, room, inDate, outDate);
        orderBill.setGuests(guest);
        orderBill.setState("已入住");
        orderBill.setCheckIn(new Date());
        orderRepository.save(orderBill);
        return orderBill;
    }


    public List<OrderBill> findAllMyOrders(String username) {
        List<OrderBill> orderBills = orderRepository.findByUsernameOrderByMakeTimeDesc(username);
        return orderBills;
    }


    private String generateAnAvalibleId() {
        String result = "";
        int alreadyHasThisKey = 0;
        do {
            for (int i = 0; i < 7; i++) {
                result += (int) (Math.random() * 10);
            }
            alreadyHasThisKey = orderRepository.findByCardNum(result);
        } while (alreadyHasThisKey != 0);
        return result;
    }

    public OrderBill updateOrderState(String no, List<String> name) {
        OrderBill orderBill = orderRepository.findOne(no);
        orderBill.setGuests(name);
        orderBill.setState("已入住");
        orderBill.setCheckIn(new Date());
        orderRepository.save(orderBill);
        return orderBill;
    }

    public void updateOrderState(String no) {
        OrderBill orderBill = orderRepository.findOne(no);
        orderBill.setCheckOut(new Date());
        orderBill.setState("已离店");
        orderRepository.save(orderBill);
    }
}
