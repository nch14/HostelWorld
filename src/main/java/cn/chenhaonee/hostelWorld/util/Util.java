package cn.chenhaonee.hostelWorld.util;

import cn.chenhaonee.hostelWorld.domain.BinaryDataDouble;
import cn.chenhaonee.hostelWorld.domain.TTO;
import cn.chenhaonee.hostelWorld.domain.TTODouble;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nichenhao on 2017/3/22.
 */
public class Util {

    public static List<TTO> parse(List<String> list) {
        List<TTO> ttos = new ArrayList<>();
        for (int i = 1; i < list.size(); i++) {
            String s = list.get(i);
            int count = 1;
            for (int j = i + 1; j < list.size(); j++) {
                String next = list.get(j);
                if (s.equals(next)) {
                    count++;
                    list.remove(j);
                    j--;
                }
            }
            ttos.add(new TTO(s, count));
        }
        return ttos;
    }

    public static List<BinaryDataDouble> parseToSum(List<BinaryDataDouble> list) {
        List<BinaryDataDouble> ttos = new ArrayList<>();
        for (int i = 1; i < list.size(); i++) {
            String s = list.get(i).getName();
            double sum = list.get(i).getValue();
            for (int j = i + 1; j < list.size(); j++) {
                String next = list.get(j).getName();
                if (s.equals(next)) {
                    sum += list.get(j).getValue();
                    list.remove(j);
                    j--;
                }
            }
            ttos.add(new BinaryDataDouble(s, sum));
        }
        return ttos;
    }

}
