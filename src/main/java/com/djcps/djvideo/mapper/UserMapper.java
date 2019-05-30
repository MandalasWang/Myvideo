package com.djcps.djvideo.mapper;

import com.djcps.djvideo.domain.User;
import com.djcps.djvideo.domain.Video;
import com.djcps.djvideo.provider.UserProvider;
import com.djcps.djvideo.provider.VideoProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 * 用户dao层
 * @author 有缘
 */
public interface UserMapper {
    /**
     * 保存订单
     * @param user
     * @return
     */
    @Insert("INSERT INTO `user` ( `openid`, `name`, " +
            "`head_img`, `phone`, `sign`, `sex`," +
            " `city`, `create_time`)" +
            "VALUES" +
            "(#{openid}, #{name}, #{headImg}, #{phone}, #{sign},#{sex}" +
            ",#{city},#{createTime});")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    int save(User user);

    /**
     * 根据主键id查找
     * @param userId
     * @return
     */
    @Select("select * from user where id = #{id}")
    User findByid(@Param("id") int userId);

    /**
     * 根据openid找用户
     * @param openid
     * @return
     */
    @Select("select * from user where openid = #{openid}")
    User findByopenid(@Param("openid") String openid);

    /**
     * 动态更新用户信息
     * @param user
     * @return
     */
    @UpdateProvider(type = UserProvider.class,method = "updateUser")
    int update(User user);


}
