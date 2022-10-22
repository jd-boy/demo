package org.jd.demo.lucene;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LuceneMain {

    public static void main(String[] args) {
        List<LPeopleDo> list = new ArrayList<>();
        list.add(new LPeopleDo(1, "张三", "法外狂徒", "string1", Arrays.asList(1, 2, 77)));
        list.add(new LPeopleDo(2, "李四", "附近v结果i集", "string2", Arrays.asList(3, 4)));
        list.add(new LPeopleDo(3, "小明", "哦额外哦容嬷嬷", "string3", Arrays.asList(5, 6)));
        list.add(new LPeopleDo(4, "xiaohong", "南方i恶化姑姑好呢", "string4", Arrays.asList(7, 8)));
        list.add(new LPeopleDo(5, "王5", "减肥i嗯份i呢", "string5", Arrays.asList(9, 10)));
        list.add(new LPeopleDo(6, "张三三", "这是一个好人", "string6", Arrays.asList(11, 12)));
        for (LPeopleDo people : list) {
            people.save();
        }

//        LPeopleDo.update("xiaohong");
//        LPeopleDo.findByFuzzy(Fields.luckyNumber, "7");
//        LPeopleDo.findByTerm(LPeopleDo.Fields.stringValue, "jjj5");
//        LPeopleDo.findByIdRange(LPeopleDo.Fields.id, 1, 5);
        LPeopleDo.findByCompound();
        LPeopleDo.delAll();
    }

}