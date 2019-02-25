package com.huilianyi.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.huilianyi.domain.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper extends BaseMapper<User> {

    User findByUsername(@Param("username") String username);
}
