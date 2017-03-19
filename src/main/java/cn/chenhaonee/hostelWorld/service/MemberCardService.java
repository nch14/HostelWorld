package cn.chenhaonee.hostelWorld.service;

import cn.chenhaonee.hostelWorld.dao.MemberCardRepository;
import cn.chenhaonee.hostelWorld.model.Member.MemberCard;
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
