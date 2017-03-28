package cn.chenhaonee.hostelWorld.util;

import cn.chenhaonee.hostelWorld.domain.BinaryDataDouble;
import cn.chenhaonee.hostelWorld.domain.BinaryData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nichenhao on 2017/3/22.
 */
public class Util {

    public static List<BinaryData> parse(List<String> list) {
        List<BinaryData> binaryDatas = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
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
            binaryDatas.add(new BinaryData(s, count));
        }
        return binaryDatas;
    }

    public static List<BinaryDataDouble> parseToSum(List<BinaryDataDouble> list) {
        List<BinaryDataDouble> ttos = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
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
