package cn.chenhaonee.hostelWorld.service;

import cn.chenhaonee.hostelWorld.dao.MoneyGiveBackDao;
import cn.chenhaonee.hostelWorld.dao.PullRequestRepository;
import cn.chenhaonee.hostelWorld.domain.TTO;
import cn.chenhaonee.hostelWorld.domain.TTODouble;
import cn.chenhaonee.hostelWorld.model.Inn.Inn;
import cn.chenhaonee.hostelWorld.model.Inn.Room;
import cn.chenhaonee.hostelWorld.model.Member.Member;
import cn.chenhaonee.hostelWorld.model.MoneyGiveBack;
import cn.chenhaonee.hostelWorld.model.common.OrderBill;
import cn.chenhaonee.hostelWorld.model.common.PullRequest;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import sun.dc.pr.PRError;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by nichenhao on 2017/3/21.
 */
@Service
public class ManagerService {
    @Autowired
    private PullRequestRepository pullRequestRepository;

    @Autowired
    private InnService innService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MoneyGiveBackDao moneyGiveBackDao;

    public List<PullRequest> getAllRequests() {
        List<PullRequest> requests = pullRequestRepository.findByAvalibleDateIsNull();
        return requests;
    }

    public void confirm(Long id) {
        PullRequest request = pullRequestRepository.findOne(id);
        request.setAvalibleDate(Calendar.getInstance().getTime());
        request.setType("已生效");
        pullRequestRepository.save(request);

        if (request.getInnId() != null) {
            Inn inn = innService.findOne(request.getInnId());
            innService.updateInn(request.getNameForInnOwner(), request.getNameForInn(), request.getTelNumber(),
                    request.getAddress(), request.getHostelDesc(), request.getEmailAddress(), request.getWideBed(),
                    request.getDoubleBed(), request.getSeaBed(), inn);
        } else {
            //新建
            /**
             * String ownerName, String hostelName, String hostelTel,
             String hostelAdd, String hostelDesc, String hostelEmail, String wideBed,
             String doubleBed, String seaBed
             */
            Inn inn = innService.createInn(request.getNameForInnOwner(), request.getNameForInn(), request.getTelNumber(),
                    request.getAddress(), request.getHostelDesc(), request.getEmailAddress(), request.getWideBed(),
                    request.getDoubleBed(), request.getSeaBed());

        }

    }

    public void deny(Long id) {
        PullRequest request = pullRequestRepository.findOne(id);
        request.setAvalibleDate(Calendar.getInstance().getTime());
        request.setType("已拒绝");
        pullRequestRepository.save(request);

    }

    public List<TTO> roomType() {
        List<Inn> inns = innService.getAllInns();
        List<String> roomTypes = new ArrayList<>();
        for (Inn i : inns) {
            List<Room> rooms = i.getRooms();
            roomTypes.addAll(rooms.stream().map(room -> room.getRoomType()).collect(Collectors.toList()));
        }
        return parse(roomTypes);
    }

    public List<TTO> innSales() {
        List<OrderBill> bills = orderService.findAll();
        List<String> count = bills.stream().map(orderBill -> {
            String innId = orderBill.getInn();
            return innService.findOne(innId).getNameForInn();
        }).collect(Collectors.toList());
        return parse(count);
    }

    public List<Member> allUsers() {
        return memberService.findAll();
    }

    public List<TTODouble> moneyToReturn() {
        List<Inn> inns = innService.getAllInns();
        List<TTODouble> values = inns.stream().map(inn -> {

            List<OrderBill> bills = orderService.findAllStoreOrders(inn.getId());
            double money = bills.stream().filter(orderBill -> orderBill.getUsername() != null).map(orderBill -> orderBill.getCost()).reduce(Double::sum).get();

            double alreadyPaid = 0;
            MoneyGiveBack moneyGiveBack = moneyGiveBackDao.findByInnName(inn.getId());
            if (moneyGiveBack != null)
                alreadyPaid = moneyGiveBack.getAlreadyPaid();
            TTODouble tto = new TTODouble(inn.getId(), inn.getNameForInn(), money - alreadyPaid);
            return tto;

        }).collect(Collectors.toList());
        return values;
    }

    public void returnMoney(String innId, double money) {
        MoneyGiveBack moneyGiveBack = moneyGiveBackDao.findByInnName(innId);
        if (moneyGiveBack == null)
            moneyGiveBack = new MoneyGiveBack(innId, money);
        else
            moneyGiveBack.addAlreadyPaid(money);
        moneyGiveBackDao.save(moneyGiveBack);
    }

    private List<TTO> parse(List<String> list) {
        List<TTO> ttos = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            String s = list.get(i);
            int count = 1;
            for (int j = i + 1; j < list.size(); j++) {
                String next = list.get(j);
                if (s.equals(next)) {
                    count++;
                    list.remove(j);
                    j--;
                }
            }
            ttos.add(new TTO(s, count));
        }
        return ttos;
    }

}
