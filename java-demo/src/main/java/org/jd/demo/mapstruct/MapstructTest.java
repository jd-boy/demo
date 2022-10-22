package org.jd.demo.mapstruct;

public class MapstructTest {

    public static void main(String[] args) {
        MPeople people = new MPeople(1, "zhangsan");
        MPeopleDto dto = MPeopleMapper.INSTANCE.toDto(people);
        System.out.println(dto);
    }

}
