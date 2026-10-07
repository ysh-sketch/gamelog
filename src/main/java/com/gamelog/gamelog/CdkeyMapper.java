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
public interface CdkeyMapper {
    @Select("SELECT id, game_id, code, status, price, created_at FROM cdkeys")
    List<Cdkey> findAll();

    @Insert("INSERT INTO cdkeys (game_id, code, price) VALUES (#{gameId}, #{code}, #{price})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Cdkey cdkey);

    @Select("SELECT id, game_id, code, status, price, created_at FROM cdkeys WHERE id = #{id}")
    Cdkey findById(Long id);

    @Update("UPDATE cdkeys SET status = '已售' WHERE status = '未售' and id = #{id}")
    int updateStatus(@Param("id") Long id);

    @Delete("DELETE FROM cdkeys WHERE id = #{id}")
    int delete(Long id);

}