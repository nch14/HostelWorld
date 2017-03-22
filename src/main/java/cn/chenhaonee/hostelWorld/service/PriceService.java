package cn.chenhaonee.hostelWorld.service;

import cn.chenhaonee.hostelWorld.dao.PriceDao;
import cn.chenhaonee.hostelWorld.model.common.Price;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by nichenhao on 2017/3/21.
 */
@Service
public class PriceService {

    @Autowired
    private PriceDao priceDao;

    public double getPrice(String ownerName, String roomType) {
        Price price = priceDao.findByInnOwnerNameAndRoomType(ownerName, roomType);
        return price.getPrice();
    }

}
