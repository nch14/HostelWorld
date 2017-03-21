package cn.chenhaonee.hostelWorld.service;

import cn.chenhaonee.hostelWorld.dao.InnOwnerRepository;
import cn.chenhaonee.hostelWorld.domain.RoomForClient;
import cn.chenhaonee.hostelWorld.exception.NoEnoughBalanceException;
import cn.chenhaonee.hostelWorld.exception.NoSuchVisaCardException;
import cn.chenhaonee.hostelWorld.exception.VisaCardCheckFailureException;
import cn.chenhaonee.hostelWorld.model.Inn.Inn;
import cn.chenhaonee.hostelWorld.model.Inn.InnOwner;
import cn.chenhaonee.hostelWorld.model.Inn.Room;
import cn.chenhaonee.hostelWorld.model.Member.Member;
import cn.chenhaonee.hostelWorld.model.Member.MemberCard;
import cn.chenhaonee.hostelWorld.model.Member.VisaCard;
import cn.chenhaonee.hostelWorld.model.common.OrderBill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.dc.pr.PRError;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by nichenhao on 2017/3/20.
 */
@Service
public class InnOwnerService {
    @Autowired
    private InnService innService;

    @Autowired
    private VisaService visaService;

    @Autowired
    private InnOwnerRepository ownerRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private MemberCardService memberCardService;

    public InnOwner addNewOwner(String username, String passwordHash, String hostelName, String hostelTel,
                                String hostelAdd, String hostelDesc, String hostelEmail, String wideBed,
                                String doubleBed, String seaBed, String hostelCard) {
        VisaCard visaCard = visaService.getVisaCard(hostelCard);

        Inn inn = innService.createInn(username, hostelName, hostelTel, hostelAdd, hostelDesc, hostelEmail,
                wideBed, doubleBed, seaBed);

        InnOwner owner = new InnOwner(username, passwordHash, visaCard, inn);
        ownerRepository.save(owner);
        return owner;
    }

    public void checkIn(String no, List<String> name, boolean payInCard) {
        //更新订单
        OrderBill orderBill = orderService.updateOrderState(no, name);
        String username = orderBill.getUsername();
        double cost = orderBill.getCost();
        //更新金额
        if (payInCard) {
            boolean success = memberCardService.consumes(username, cost);
        }
    }

    public String checkIn(String username, String hotelId, Long roomId, Date inDate, Date outDate, List<String> name, boolean payInCard) {
        //新建订单
        OrderBill orderBill = orderService.makeAnOrder(username, hotelId, roomId, inDate, outDate, name);
        double cost = orderBill.getCost();
        //更新金额
        if (username != null && payInCard) {
            boolean success = memberCardService.consumes(username, cost);
        }

        return orderBill.getId();
    }

    public void checkOut(String no) {
        //更新订单
        orderService.updateOrderState(no);
    }

    public Long findRoomId(String username, String roomName) {
        InnOwner owner = ownerRepository.findOne(username);
        Inn inn = owner.getInn();
        List<Room> rooms = inn.getRooms();
        for (Room room : rooms) {
            if (room.getRoomName().equals(roomName))
                return room.getId();
        }
        return null;
    }

    public String findInnId(String username) {
        InnOwner owner = ownerRepository.findOne(username);
        Inn inn = owner.getInn();
        return inn.getId();
    }

    public List<String> findRooms(String username, String roomType, String fromDate, String toDate) {
        InnOwner owner = ownerRepository.findOne(username);
        Inn inn = owner.getInn();

        List<RoomForClient> roomForClients = innService.getRoomList(inn.getId(), fromDate, toDate);
        List<String> roomList = roomForClients.stream().filter(room -> room.getRoomType().equals(roomType)).map(room -> room.getRoomName()).collect(Collectors.toList());

        return roomList;
    }

}
