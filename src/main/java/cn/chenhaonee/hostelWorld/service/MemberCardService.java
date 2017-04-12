package cn.chenhaonee.hostelWorld.service;

import cn.chenhaonee.hostelWorld.repository.MemberCardRepository;
import cn.chenhaonee.hostelWorld.model.Member.MemberCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by carlos on 2017/3/14.
 */
@Service
public class MemberCardService {
    @Autowired
    private MemberCardRepository cardRepository;
    @Value("${member.card.levelOneMarks}")
    private int levelOneMarks;
    @Value("${member.card.levelOneDiscount}")
    private double levelOneDiscount;
    @Value("${member.card.levelTwoMarks}")
    private int levelTwoMarks;
    @Value("${member.card.levelTwoDiscount}")
    private double levelTwoDiscount;
    @Value("${member.card.levelThreeMarks}")
    private int levelThreeMarks;
    @Value("${member.card.levelThreeDiscount}")
    private double levelThreeDiscount;
    @Value("${member.card.levelFourMarks}")
    private int levelFourMarks;
    @Value("${member.card.levelFourDiscount}")
    private double levelFourDiscount;

    public MemberCard findOne(String cardNum){
        return cardRepository.findOne(cardNum);
    }

    public boolean consumes(String cardNum, double money) {
        MemberCard memberCard = cardRepository.findOne(cardNum);
        double balance = memberCard.getBalance();
        double sumCost = memberCard.getSumCost();
        int marks = memberCard.getCurrentMarks();
        if (balance < money) {
            return false;
        } else {
            memberCard.setBalance(balance - money);
            memberCard.setSumCost(sumCost + money);
            memberCard.setCurrentMarks(marks + (int) money);
            cardRepository.save(memberCard);
            return true;
        }
    }


    public MemberCard createCard(double money) {
        MemberCard memberCard = new MemberCard(generateAnAvalibleId(), money);
        cardRepository.save(memberCard);
        return memberCard;
    }


    private String generateAnAvalibleId() {
        String result = "";
        int alreadyHasThisKey = 0;
        do {
            for (int i = 0; i < 7; i++) {
                result += (int) (Math.random() * 10);
            }
            alreadyHasThisKey = cardRepository.findByCardNum(result);
        } while (alreadyHasThisKey != 0);
        return result;
    }

    public String getLevel(double sumCost) {
        if (sumCost < levelTwoMarks)
            return "普通会员";
        if (sumCost < levelThreeMarks)
            return "银卡会员";
        if (sumCost < levelFourMarks)
            return "金卡会员";
        return "钻石会员";
    }

    public String getDiscountInString(double sumCost) {
        if (sumCost < levelTwoMarks)
            return "九折";
        if (sumCost < levelThreeMarks)
            return "八折";
        if (sumCost < levelFourMarks)
            return "7折";
        return "五折";
    }

    public double getDiscount(double sumCost) {
        if (sumCost < levelTwoMarks)
            return levelOneDiscount;
        if (sumCost < levelThreeMarks)
            return levelTwoDiscount;
        if (sumCost < levelFourMarks)
            return levelThreeDiscount;
        return levelFourDiscount;
    }
}
