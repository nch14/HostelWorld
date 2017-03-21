package cn.chenhaonee.hostelWorld.service;

import cn.chenhaonee.hostelWorld.dao.InnRepository;
import cn.chenhaonee.hostelWorld.dao.RoomRepository;
import cn.chenhaonee.hostelWorld.domain.InnForClient;
import cn.chenhaonee.hostelWorld.domain.RoomForClient;

import cn.chenhaonee.hostelWorld.model.Inn.Inn;
import cn.chenhaonee.hostelWorld.model.Inn.Room;
import cn.chenhaonee.hostelWorld.model.Member.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by nichenhao on 2017/3/20.
 */
@Service
public class InnService {

    @Autowired
    private InnRepository innRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private RoomRepository roomRepository;

    public List<InnForClient> getInnList() {
        List<InnForClient> inns = innRepository
                .findAll()
                .stream()
                .map(inn -> new InnForClient(inn.getId(), inn.getNameForInn()))
                .collect(Collectors.toList());
        return inns;
    }


    public Inn createInn(String username, String hostelName, String hostelTel,
                         String hostelAdd, String hostelDesc, String hostelEmail, String wideBed,
                         String doubleBed, String seaBed) {
        List<Room> rooms = new ArrayList<>();
        rooms.addAll(Arrays.stream(wideBed.split(" ")).map(s -> new Room(s, "浴缸大床房")).collect(Collectors.toList()));
        rooms.addAll(Arrays.stream(doubleBed.split(" ")).map(s -> new Room(s, "标准双床房")).collect(Collectors.toList()));
        rooms.addAll(Arrays.stream(seaBed.split(" ")).map(s -> new Room(s, "海景休闲房")).collect(Collectors.toList()));
        roomRepository.save(rooms);

        Inn inn = new Inn(generateAnAvalibleId(), hostelName, username, hostelTel, hostelAdd, hostelEmail, hostelDesc, rooms);
        innRepository.save(inn);
        return inn;
    }


    public List<RoomForClient> getRoomList(String innId, String start, String end) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date from = null;
        Date to = null;
        try {
            from = sdf.parse(start);
            to = sdf.parse(end);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //Inn inn = innRepository.findOne(id);

        List<String> roomsUnavalible = orderService.getUnavalibleRoomNames(innId, from, to);

        List<RoomForClient> rooms = innRepository.findOne(innId).getRooms()
                .stream()
                .filter(room -> filter(room.getRoomName(), roomsUnavalible))
                .map(room -> new RoomForClient(room.getId(), room.getRoomName(), room.getRoomType(), 0.0))
                .collect(Collectors.toList());

        return rooms;
    }

    /**
     * 过滤掉已经被预定了或入住的房间
     *
     * @param s
     * @param roomUnavalible
     * @return
     */
    private boolean filter(String s, List<String> roomUnavalible) {
        if (roomUnavalible.contains(s))
            return false;
        else
            return true;
    }

    private String generateAnAvalibleId() {
        String result = "";
        int alreadyHasThisKey = 0;
        do {
            for (int i = 0; i < 7; i++) {
                result += (int) (Math.random() * 10);
            }
            alreadyHasThisKey = innRepository.findByInnNum(result);
        } while (alreadyHasThisKey != 0);
        return result;
    }
}
