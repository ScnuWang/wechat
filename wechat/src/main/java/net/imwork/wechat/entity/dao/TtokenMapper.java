package net.imwork.wechat.entity.dao;

import net.imwork.wechat.entity.model.Ttoken;
import org.apache.ibatis.annotations.Param;

public interface TtokenMapper {

    int insert(Ttoken ttoken);

    int updateByPrimaryKey(Ttoken ttoken);

    Ttoken selectByTokenType(@Param("tokenType") Integer tokenType);
}