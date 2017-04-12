package cn.chenhaonee.hostelWorld.service;

import cn.chenhaonee.hostelWorld.repository.PriceRepository;
import cn.chenhaonee.hostelWorld.model.common.Price;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by nichenhao on 2017/3/21.
 */
@Service
public class PriceService {

    @Autowired
    private PriceRepository priceRepository;

    public double getPrice(String ownerName, String roomType) {
        Price price = priceRepository.findByInnOwnerNameAndRoomType(ownerName, roomType);
        return price.getPrice();
    }

}
