package cn.chenhaonee.hostelWorld.init;

import cn.chenhaonee.hostelWorld.dao.VisaCardRepository;
import cn.chenhaonee.hostelWorld.model.Member.VisaCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by carlos on 2017/3/14.
 */
@Service
public class InitVisaCard {
    @Autowired
    private VisaCardRepository visaCardRepository;

    public void init() {
        visaCardRepository.deleteAll();
        List<VisaCard> visaCards = new ArrayList<>();
        visaCards.add(new VisaCard("1111111111111111","233","09/33", 500000));
        visaCards.add(new VisaCard("2222222222222222","233","09/33",  500000));
        visaCardRepository.save(visaCards);
    }
}
