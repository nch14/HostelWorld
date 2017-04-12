package cn.chenhaonee.hostelWorld.util;

import cn.chenhaonee.hostelWorld.repository.MemberShipValidRepository;
import cn.chenhaonee.hostelWorld.exception.NoSuchUserException;
import cn.chenhaonee.hostelWorld.model.Member.MemberCard;
import cn.chenhaonee.hostelWorld.model.MemberShipValid;
import cn.chenhaonee.hostelWorld.service.MemberCardService;
import cn.chenhaonee.hostelWorld.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by chenhaonee on 2017/3/27.
 */
@Service
public class MemberStateChecker {

    @Autowired
    private MemberShipValidRepository memberShipValidRepository;

    @Autowired
    private MemberCardService memberCardService;

    @Autowired
    private MemberService memberService;

    public void run() {
        checkMemberState();
    }

    private void checkMemberState() {
        ExecutorService es = Executors.newSingleThreadExecutor();
        es.submit(() -> {
            while (true) {
                List<MemberShipValid> memberShipValids = memberShipValidRepository.findAll();
                LocalDate today = LocalDate.now();
                Date date = new Date(today.getYear(), today.getMonthValue(), today.getDayOfMonth());
                memberShipValids.stream()
                        .filter(memberShipValid -> memberShipValid.getNextCheckTime().equals(date))
                        .forEach(memberShipValid -> {
                            MemberCard card = memberCardService.findOne(memberShipValid.getMemberCardNum());
                            if (card.getBalance() > 0)
                                memberShipValid.setNextCheckTime(new Date(today.getYear(), today.getMonthValue(), today.getDayOfMonth()));
                            else {
                                if (memberShipValid.getState() == MemberShipValid.IN_USE) {
                                    memberShipValid.setState(MemberShipValid.STOP);
                                    memberShipValid.setNextCheckTime(new Date(today.getYear(), today.getMonthValue(), today.getDayOfMonth()));
                                } else if (memberShipValid.getState() == MemberShipValid.STOP)
                                    memberShipValid.setState(MemberShipValid.DESTROY);
                                try {
                                    memberService.vitiateMembership(memberShipValid.getUsername());
                                } catch (NoSuchUserException e) {
                                    e.printStackTrace();
                                }
                            }
                            memberShipValidRepository.save(memberShipValid);
                        });
                Thread.sleep(24*3600*1000);
            }
        });
    }
}
