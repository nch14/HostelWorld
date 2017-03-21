package cn.chenhaonee.hostelWorld.service;

import cn.chenhaonee.hostelWorld.dao.MemberCardRepository;
import cn.chenhaonee.hostelWorld.model.Member.MemberCard;
import cn.chenhaonee.hostelWorld.model.Member.VisaCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

/**
 * Created by carlos on 2017/3/14.
 */
@Service
@ConfigurationProperties(prefix = "member.card")
public class MemberCardService {
    @Autowired
    private MemberCardRepository cardRepository;

    private int levelOneMarks;
    private double levelOneDiscount;

    private int levelTwoMarks;
    private double levelTwoDiscount;

    private int levelThreeMarks;
    private double levelThreeDiscount;

    private int levelFourMarks;
    private double levelFourDiscount;

    public boolean consumes(String cardNum, double money) {
        MemberCard memberCard = cardRepository.findOne(cardNum);
        double balance = memberCard.getBalance();
        if (balance < money)
            return false;
        else {
            memberCard.setBalance(balance - money);
            cardRepository.save(memberCard);
            return true;
        }
    }


    public MemberCard createCard(double money) {
        MemberCard memberCard = new MemberCard(generateAnAvalibleId(),money);
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

    public String getLevel(double sumCost){
        if (sumCost<levelTwoMarks)
            return "普通会员";
        if (sumCost<levelThreeMarks)
            return "银卡会员";
        if (sumCost<levelFourMarks)
            return "金卡会员";
        return "钻石会员";
    }

    public String getDiscountInString(double sumCost){
        if (sumCost<levelTwoMarks)
            return "九折";
        if (sumCost<levelThreeMarks)
            return "八折";
        if (sumCost<levelFourMarks)
            return "7折";
        return "五折";
    }

    public double getDiscount(double sumCost){
        if (sumCost<levelTwoMarks)
            return levelOneDiscount;
        if (sumCost<levelThreeMarks)
            return levelTwoDiscount;
        if (sumCost<levelFourMarks)
            return levelThreeDiscount;
        return levelFourDiscount;
    }
}
