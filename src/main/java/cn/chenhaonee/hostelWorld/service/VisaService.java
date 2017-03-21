package cn.chenhaonee.hostelWorld.service;

import cn.chenhaonee.hostelWorld.dao.VisaCardRepository;
import cn.chenhaonee.hostelWorld.exception.NoSuchVisaCardException;
import cn.chenhaonee.hostelWorld.exception.VisaCardCheckFailureException;
import cn.chenhaonee.hostelWorld.model.Member.VisaCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by nichenhao on 2017/3/19.
 */
@Service
public class VisaService {
    @Autowired
    private VisaCardRepository cardRepository;

    public VisaCard getVisaCard(String cardNum, String validDate, String cvv) throws NoSuchVisaCardException, VisaCardCheckFailureException {
        VisaCard visaCard = cardRepository.findOne(cardNum);
        if (visaCard == null)
            throw new NoSuchVisaCardException();
        if (visaCard.getValidDate().equals(validDate) && visaCard.getCvv().equals(cvv))
            return visaCard;
        else
            throw new VisaCardCheckFailureException();
    }

    public VisaCard getVisaCard(String cardNum) {
        return cardRepository.findOne(cardNum);
    }

    public boolean consumes(String cardNum, double money) {
        VisaCard visaCard = cardRepository.findOne(cardNum);
        double balance = visaCard.getBalance();
        if (balance < money)
            return false;
        else {
            visaCard.setBalance(balance - money);
            cardRepository.save(visaCard);
            return true;
        }
    }
}
