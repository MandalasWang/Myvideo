package com.djcps.djvideo.provider;

import com.djcps.djvideo.domain.User;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author 有缘
 */
public class UserProvider {

    /**
     * 更新video动态语句
     * @param user
     * @return
     */
    public String updateUser(final User user){
        return new SQL(){{
            UPDATE("user");
            //条件写法.
            if(user.getName()!= null){
                SET("name=#{name}");
            }
            if(user.getHeadImg()!= null){
                SET("coverImg=#{headImg}");
            }
            if(user.getSex()!= null){
                SET("sex=#{sex}");
            }
            WHERE("id=#{id}");
        }}.toString();
    }
}
