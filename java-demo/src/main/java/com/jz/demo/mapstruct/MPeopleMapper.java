package com.jz.demo.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MPeopleMapper {

    MPeopleMapper INSTANCE = Mappers.getMapper(MPeopleMapper.class);

    MPeopleDto toDto(MPeople mPeople);

}
