package com.djcps.djvideo.mapper;

import com.djcps.djvideo.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * 用户dao层
 */
public interface UserMapper {

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


}
