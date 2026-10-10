package com.gamelog.gamelog;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LikeMapper {

    @Select("SELECT id, user_id, game_id, created_at FROM likes WHERE user_id = #{userId} AND game_id = #{gameId}")
    Like findById(@Param("userId") Long userId, @Param("gameId") Long gameId);

    @Insert("INSERT INTO likes (user_id, game_id) VALUES (#{userId}, #{gameId}) ON CONFLICT (game_id, user_id) DO NOTHING")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Like like);

    @Delete("DELETE FROM likes WHERE user_id = #{userId} AND game_id = #{gameId}")
    int delete(@Param("userId") Long userId, @Param("gameId") Long gameId);
}


