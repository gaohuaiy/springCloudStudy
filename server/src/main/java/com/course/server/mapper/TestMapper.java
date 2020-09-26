package com.course.server.mapper;

import com.course.server.domain.Test;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TestMapper {
    public List<Test> list();
}
