package com.djcps.djvideo.mapper;

import com.djcps.djvideo.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;

public interface UserMapper {

    @Insert("INSERT INTO `user` ( `openid`, `name`, " +
            "`head_img`, `phone`, `sign`, `sex`," +
            " `city`, `create_time`)" +
            "VALUES" +
            "(#{openid}, #{name}, #{headImg}, #{phone}, #{sign},#{sex}" +
            ",#{city},#{createTime});")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    int save(User user);


}
