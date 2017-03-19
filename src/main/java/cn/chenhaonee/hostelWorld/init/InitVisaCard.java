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
        List<VisaCard> visaCards = new ArrayList<>();
        visaCards.add(new VisaCard("350119962248", 500000));
        visaCards.add(new VisaCard("350119962348", 500000));
        visaCards.add(new VisaCard("350119962448", 1500));
        visaCards.add(new VisaCard("350119962548", 500));
        visaCards.add(new VisaCard("350119962648", 2500));
        visaCardRepository.save(visaCards);
    }
}
