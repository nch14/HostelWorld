package cn.chenhaonee.hostelWorld.service;

import cn.chenhaonee.hostelWorld.dao.OrderRepository;
import cn.chenhaonee.hostelWorld.dao.PriceDao;
import cn.chenhaonee.hostelWorld.dao.RoomArragementDao;
import cn.chenhaonee.hostelWorld.model.Inn.Inn;
import cn.chenhaonee.hostelWorld.model.Inn.Room;
import cn.chenhaonee.hostelWorld.model.Member.Member;
import cn.chenhaonee.hostelWorld.model.common.OrderBill;
import cn.chenhaonee.hostelWorld.model.common.Price;
import cn.chenhaonee.hostelWorld.model.common.RoomArrangement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
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

    @Autowired
    private RoomArragementDao roomArragementDao;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberCardService memberCardService;

    @Autowired
    private PriceDao priceDao;

    @Autowired
    private InnService innService;

    public OrderBill findOne(String id) {
        return orderRepository.findOne(id);
    }

    public List<OrderBill> getTodayRoomRelativeOrders(String inn, Date today) {
        List<OrderBill> theseDaysOrderBills = orderRepository.findByInnAndTime(inn, today);
        return theseDaysOrderBills;
    }

/*    public List<String> getUnavalibleRoomNames(String inn, Date start, Date end) {
        List<OrderBill> theseDaysOrderBills = orderRepository.findByInnAndArrivalDateBeforeAndLeaveDateAfter(inn, end, start);
        List<String> rooms = theseDaysOrderBills.stream()
                .map(orderBill -> orderBill.getRoom().getRoomName())
                .collect(Collectors.toList());
        return rooms;
    }*/

    public List<String> getUnavalibleRoomNames(String inn, Date inDate, Date outDate) {
        List<Long> roomIds = new ArrayList<>();
        int days = (int) ((outDate.getTime() - inDate.getTime()) / 1000 * 60 * 60 * 24);
        for (int i = 0; i < days; i++) {
            Date thisDay = new Date(inDate.getTime() + i * 1000 * 60 * 60 * 24);
            List<RoomArrangement> roomArrangements = roomArragementDao.findByInnNameAndDate(inn, thisDay);
            roomIds.addAll(roomArrangements.stream().map(roomArrangement -> roomArrangement.getRoomId()).collect(Collectors.toList()));
        }

        return roomIds.stream().distinct().map(l -> roomService.getRoom(l).getRoomName()).collect(Collectors.toList());
    }

    public void makeAnOrder(String username, String hotelId, Long roomId, Date inDate, Date outDate) {
        // TODO: 2017/3/21 计算cost
        Room room = roomService.getRoom(roomId);
        Inn inn = innService.findOne(hotelId);

        Member member = memberService.findOne(username);
        double discount = memberCardService.getDiscount(member.getMemberCard().getSumCost());

        int days = (int) ((outDate.getTime() - inDate.getTime()) / 1000 * 60 * 60 * 24);

        Price price = priceDao.findByInnOwnerNameAndRoomType(inn.getNameForInnOwner(), room.getRoomType());
        double cost = days * price.getPrice() * discount;


        OrderBill orderBill = new OrderBill(generateAnAvalibleId(), username, hotelId, room, inDate, outDate);
        orderBill.setState("已预订");
        orderBill.setCost(cost);
        orderRepository.save(orderBill);

        for (int i = 0; i < days; i++) {
            Date thisDay = new Date(inDate.getTime() + i * 1000 * 60 * 60 * 24);
            RoomArrangement roomArrangement = new RoomArrangement(room.getId(), username, hotelId, thisDay);
            roomArragementDao.save(roomArrangement);
        }

    }

    public OrderBill makeAnOrder(String username, String hotelId, Room room, Date inDate, Date outDate, List<String> guest) {
        OrderBill orderBill = new OrderBill(generateAnAvalibleId(), username, hotelId, room, inDate, outDate);
        orderBill.setGuests(guest);
        orderBill.setState("已入住");
        orderBill.setCheckIn(new Date());
        orderRepository.save(orderBill);

        int days = (int) ((outDate.getTime() - inDate.getTime()) / (1000 * 60 * 60 * 24));
        for (int i = 0; i < days; i++) {
            Date thisDay = new Date(inDate.getTime() + i * 1000 * 60 * 60 * 24);
            RoomArrangement roomArrangement = new RoomArrangement(room.getId(), username, hotelId, thisDay);
            roomArragementDao.save(roomArrangement);
        }

        return orderBill;
    }

    public OrderBill makeAnOrder(String username, String hotelId, Room room, Date inDate, Date outDate, List<String> guest, double cost) {
        OrderBill orderBill = new OrderBill(generateAnAvalibleId(), username, hotelId, room, inDate, outDate);
        orderBill.setGuests(guest);
        orderBill.setState("已入住");
        orderBill.setCheckIn(new Date());
        orderBill.setCost(cost);
        orderRepository.save(orderBill);

        int days = (int) ((outDate.getTime() - inDate.getTime()) / (1000 * 60 * 60 * 24));
        for (int i = 0; i < days; i++) {
            Date thisDay = new Date(inDate.getTime() + i * 1000 * 60 * 60 * 24);
            RoomArrangement roomArrangement = new RoomArrangement(room.getId(), username, hotelId, thisDay);
            roomArragementDao.save(roomArrangement);
        }

        return orderBill;
    }

    public void cancelAnOrder(String orderId) {
        OrderBill orderBill = orderRepository.findOne(orderId);
        orderBill.setState("已取消");
        orderRepository.save(orderBill);
        Date inDate = orderBill.getArrivalDate();
        Date outDate = orderBill.getLeaveDate();

        int days = (int) ((outDate.getTime() - inDate.getTime()) / (1000 * 60 * 60 * 24));
        for (int i = 0; i < days; i++) {
            Date thisDay = new Date(inDate.getTime() + i * 1000 * 60 * 60 * 24);
            RoomArrangement roomArrangement = roomArragementDao.findByRoomIdAndInnNameAndDate(orderBill.getRoom().getId(), orderBill.getInn(), thisDay);
            if (roomArrangement != null)
                roomArragementDao.delete(roomArrangement);
        }

    }


    public List<OrderBill> findAllMyOrders(String username) {
        List<OrderBill> orderBills = orderRepository.findByUsernameOrderByMakeTimeDesc(username);
        return orderBills;
    }

    public List<OrderBill> findAllStoreOrders(String innName) {
        List<OrderBill> orderBills = orderRepository.findByInnOrderByMakeTimeDesc(innName);
        return orderBills;
    }

    public List<OrderBill> findAll() {
        List<OrderBill> orderBills = orderRepository.findAll();
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
