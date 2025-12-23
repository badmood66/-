package com.finance.financesystembackend.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.finance.financesystembackend.auth.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
