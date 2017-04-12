package cn.chenhaonee.hostelWorld.service;

import cn.chenhaonee.hostelWorld.repository.MemberRepository;
import cn.chenhaonee.hostelWorld.exception.NoEnoughBalanceException;
import cn.chenhaonee.hostelWorld.exception.NoSuchUserException;
import cn.chenhaonee.hostelWorld.exception.NoSuchVisaCardException;
import cn.chenhaonee.hostelWorld.exception.VisaCardCheckFailureException;
import cn.chenhaonee.hostelWorld.model.Member.*;
import cn.chenhaonee.hostelWorld.model.common.OrderBill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by nichenhao on 2017/3/18.
 */
@Service
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private VisaService visaService;

    @Autowired
    private MemberCardService memberCardService;

    @Autowired
    private OrderService orderService;

    public Member findOne(String username) {
        return memberRepository.findOne(username);
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    public void vitiateMembership(String memberId) throws NoSuchUserException {
        Member member = memberRepository.findOne(memberId);
        if (member == null)
            throw new NoSuchUserException();
        else {
            member.setMemberCard(null);
            member.setPasswordHash(null);
            memberRepository.save(member);
        }
    }

    /**
     * 注册为会员
     *
     * @param username
     * @param passwordHash
     * @param cardNum
     * @param validDate
     * @param cvv
     * @param money
     * @return
     * @throws VisaCardCheckFailureException
     * @throws NoSuchVisaCardException
     * @throws NoEnoughBalanceException
     */
    public Member addNewMember(String username, String passwordHash, String cardNum, String validDate, String cvv,
                               int money) throws VisaCardCheckFailureException, NoSuchVisaCardException, NoEnoughBalanceException {
        VisaCard visaCard = visaService.getVisaCard(cardNum, validDate, cvv);

        boolean payForMembership = visaService.consumes(cardNum, money);
        if (!payForMembership) {
            //没有足够的钱付款
            throw new NoEnoughBalanceException();
        }

        MemberCard memberCard = memberCardService.createCard(money);
        Member member = new Member(username, passwordHash, visaCard, memberCard);
        memberRepository.save(member);
        return member;
    }

    public void chargeMoney(String username, double money) throws NoSuchUserException, NoEnoughBalanceException {
        Member member = memberRepository.findOne(username);
        if (member == null)
            throw new NoSuchUserException();
        VisaCard visaCard = member.getVisaCard();
        MemberCard memberCard = member.getMemberCard();

        boolean success = visaService.consumes(visaCard.getCardNum(), money);
        if (!success)
            throw new NoEnoughBalanceException();
// TODO: 2017/3/19 检查卡片状态
        double balance = memberCard.getBalance();
        memberCard.setBalance(balance + money);
        memberRepository.save(member);
    }

    public List<OrderBill> getAllMyOrders(String username) {
        return orderService.findAllMyOrders(username);
    }

    public void updateCardNum(String username, String cardNum) {
        Member member = memberRepository.findOne(username);
        VisaCard visaCard = visaService.getVisaCard(cardNum);
        member.setVisaCard(visaCard);
        memberRepository.save(member);
    }
}
