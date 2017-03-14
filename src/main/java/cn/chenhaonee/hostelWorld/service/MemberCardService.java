package cn.chenhaonee.hostelWorld.service;

import cn.chenhaonee.hostelWorld.dao.MemberCardRepository;
import cn.chenhaonee.hostelWorld.model.MemberCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by carlos on 2017/3/14.
 */
@Service
public class MemberCardService {
    @Autowired
    private MemberCardRepository cardRepository;

    public MemberCard createCard() {
        MemberCard memberCard = new MemberCard(generateAnAvalibleId());
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
}
