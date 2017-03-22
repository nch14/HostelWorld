package cn.chenhaonee.hostelWorld.service;

import cn.chenhaonee.hostelWorld.dao.InnOwnerRepository;
import cn.chenhaonee.hostelWorld.dao.PriceDao;
import cn.chenhaonee.hostelWorld.dao.PullRequestRepository;
import cn.chenhaonee.hostelWorld.domain.InnDetailClient;
import cn.chenhaonee.hostelWorld.domain.RoomDetailClient;
import cn.chenhaonee.hostelWorld.domain.RoomForClient;
import cn.chenhaonee.hostelWorld.model.Inn.Inn;
import cn.chenhaonee.hostelWorld.model.Inn.InnOwner;
import cn.chenhaonee.hostelWorld.model.Inn.Room;
import cn.chenhaonee.hostelWorld.model.Member.Member;
import cn.chenhaonee.hostelWorld.model.Member.VisaCard;
import cn.chenhaonee.hostelWorld.model.common.OrderBill;
import cn.chenhaonee.hostelWorld.model.common.Price;
import cn.chenhaonee.hostelWorld.model.common.PullRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by nichenhao on 2017/3/20.
 */
@Service
public class InnOwnerService {

    @Autowired
    private PriceDao priceDao;
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

    @Autowired
    private RoomService roomService;

    @Autowired
    private PullRequestRepository requestRepository;

    @Autowired
    private MemberService memberService;

    public InnOwner findOne(String username) {
        return ownerRepository.findOne(username);
    }

    public void setInn(String username, Inn inn) {
        InnOwner owner = findOne(username);
        owner.setInn(inn);
        ownerRepository.save(owner);
    }


    public InnOwner addNewOwner(String username, String passwordHash, String hostelName, String hostelTel,
                                String hostelAdd, String hostelDesc, String hostelEmail, String wideBed,
                                String doubleBed, String seaBed, String hostelCard) {
        VisaCard visaCard = visaService.getVisaCard(hostelCard);

        PullRequest pullRequest = new PullRequest(null, hostelName, username, hostelTel, hostelAdd, hostelEmail, hostelDesc, wideBed, doubleBed, seaBed, "新建店铺");
        requestRepository.save(pullRequest);

        InnOwner owner = new InnOwner(username, passwordHash, visaCard, null);
        ownerRepository.save(owner);
        return owner;
    }

    public void checkIn(String no, List<String> name, boolean payInCard) {
        //更新订单
        OrderBill orderBill = orderService.updateOrderState(no, name);
        String username = orderBill.getUsername();
        Member member = memberService.findOne(username);
        String cardNum = member.getMemberCard().getId();

        double cost = orderBill.getCost();
        //更新金额
        if (payInCard) {
            boolean success = memberCardService.consumes(cardNum, cost);
        }
    }

    public String checkIn(String ownerName, String username, String hotelId, Long roomId, Date inDate, Date outDate, List<String> name, boolean payInCard) {
        double discount = 1;
        String cardNum = "";
        if (username != null) {
            Member member = memberService.findOne(username);
            cardNum = member.getMemberCard().getId();
            discount = memberCardService.getDiscount(member.getMemberCard().getSumCost());
        }

        //计算金额
        Room room = roomService.getRoom(roomId);
        String roomType = room.getRoomType();
        Price price = priceDao.findByInnOwnerNameAndRoomType(ownerName, roomType);
        int days = (int) ((outDate.getTime() - inDate.getTime()) / (1000 * 60 * 60 * 24));
        double cost = days * price.getPrice() * discount;

        //新建订单
        OrderBill orderBill = orderService.makeAnOrder(username, hotelId, room, inDate, outDate, name, cost);


        //更新金额
        if (username != null && payInCard) {
            boolean success = memberCardService.consumes(cardNum, cost);
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

    public List<String> findRooms(String username, String roomType, String fromDate, String toDate) throws ParseException {
        InnOwner owner = ownerRepository.findOne(username);
        Inn inn = owner.getInn();

        List<RoomForClient> roomForClients = innService.getRoomList(inn.getId(), fromDate, toDate);
        List<String> roomList = roomForClients.stream().filter(room -> room.getRoomType().equals(roomType)).map(room -> room.getRoomName()).collect(Collectors.toList());

        return roomList;
    }

    public List<RoomDetailClient> findRooms(String username) {
        InnOwner owner = ownerRepository.findOne(username);
        Inn inn = owner.getInn();

        Date fromDate = Calendar.getInstance().getTime();
        fromDate.setHours(0);
        fromDate.setMinutes(0);
        fromDate.setSeconds(0);
        List<OrderBill> orderBills = orderService.getTodayRoomRelativeOrders(inn.getId(), fromDate);

        List<RoomDetailClient> roomList = inn.getRooms().stream()
                .map(room -> {
                    String state = "";
                    List<OrderBill> bills = orderBills.stream().filter(orderBill -> orderBill.getRoom().getRoomName().equals(room.getRoomName())).collect(Collectors.toList());
                    if (bills.size() == 0)
                        state = "可用";
                    else {
                        OrderBill bill = bills.get(0);
                        if (bill.getCheckIn() != null)
                            state = "已入住";
                        else
                            state = "已预订";
                        if (bill.getCheckOut() != null)
                            state = "已离店";

                    }
                    Price price = priceDao.findByInnOwnerNameAndRoomType(username, room.getRoomType());
                    double priceValue = 0;
                    if (price != null)
                        priceValue = price.getPrice();
                    return new RoomDetailClient(room.getId(), room.getRoomName(), room.getRoomType(), priceValue, state);
                })
                .collect(Collectors.toList());

        return roomList;
    }

    public List<Double> findPrice(String username) {
        InnOwner owner = ownerRepository.findOne(username);

        List<Double> doubles = new ArrayList<>();
        Price priceForB = priceDao.findByInnOwnerNameAndRoomType(username, "浴缸大床房");
        Price priceForD = priceDao.findByInnOwnerNameAndRoomType(username, "标准双床房");
        Price priceForS = priceDao.findByInnOwnerNameAndRoomType(username, "海景休闲房");
        doubles.add(priceForB != null ? priceForB.getPrice() : 0);
        doubles.add(priceForD != null ? priceForD.getPrice() : 0);
        doubles.add(priceForS != null ? priceForS.getPrice() : 0);

        return doubles;
    }

    public void setPrice(String username, double b, double d, double s) {
        Price priceForB = priceDao.findByInnOwnerNameAndRoomType(username, "浴缸大床房");
        if (priceForB == null)
            priceDao.save(new Price(username, "浴缸大床房", b));
        else {
            priceForB.setPrice(b);
            priceDao.save(priceForB);
        }
        Price priceForD = priceDao.findByInnOwnerNameAndRoomType(username, "标准双床房");
        if (priceForD == null)
            priceDao.save(new Price(username, "标准双床房", d));
        else {
            priceForD.setPrice(d);
            priceDao.save(priceForD);
        }
        Price priceForS = priceDao.findByInnOwnerNameAndRoomType(username, "海景休闲房");
        if (priceForS == null)
            priceDao.save(new Price(username, "海景休闲房", s));
        else {
            priceForS.setPrice(s);
            priceDao.save(priceForS);
        }

    }


    public InnDetailClient getInfo(String username) {
        InnOwner owner = ownerRepository.findOne(username);
        Inn inn = owner.getInn();
        InnDetailClient innDetailClient = new InnDetailClient(inn.getId(), inn.getNameForInn(), inn.getNameForInnOwner(), inn.getTelNumber(), inn.getAddress(), inn.getEmailAddress(), inn.getApplyDate(), inn.getAvalibleDate(), inn.getHostelDesc(), inn.getRooms(), owner.getVisaCard().getCardNum());
        return innDetailClient;
    }

    public void updateInfo(String username, String hostelName, String hostelTel,
                           String hostelAdd, String hostelDesc, String hostelEmail, String wideBed,
                           String doubleBed, String seaBed, String hostelCard) {
        VisaCard visaCard = visaService.getVisaCard(hostelCard);

        InnOwner owner = ownerRepository.findOne(username);
        owner.setVisaCard(visaCard);
        Inn inn = owner.getInn();

        PullRequest pullRequest = new PullRequest(inn.getId(), hostelName, username, hostelTel, hostelAdd, hostelEmail, hostelDesc, wideBed, doubleBed, seaBed, "修改信息");
        requestRepository.save(pullRequest);

        ownerRepository.save(owner);
    }
}

