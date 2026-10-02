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
public interface GameMapper {
    @Select("SELECT id, name, platform, state, rating, reflection, created_at FROM games")
    List<Game> findAll();

    @Insert("INSERT INTO games (name, platform, state, rating, reflection) VALUES (#{name}, #{platform}, #{state}, #{rating}, #{reflection})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Game game);

    @Select("SELECT id, name, platform, state, rating, reflection, created_at FROM games WHERE id = #{id}")
    Game findById(Long id);

    @Update("UPDATE games SET platform = #{platform} WHERE id = #{id}")
    int updatePlatform(@Param("id") Long id, @Param("platform") String platform);

    @Update("UPDATE games SET state = #{state} WHERE id = #{id}")
    int updateState(@Param("id") Long id, @Param("state") String state);

    @Update("UPDATE games SET rating = #{rating} WHERE id = #{id}")
    int updateRating(@Param("id") Long id, @Param("rating") Integer rating);

    @Update("UPDATE games SET reflection = #{reflection} WHERE id = #{id}")
    int updateReflection(@Param("id") Long id, @Param("reflection") String reflection);

    @Update("UPDATE games SET name = #{name} WHERE id = #{id}")
    int updateName(@Param("id") Long id, @Param("name") String name);
    
    @Delete("DELETE FROM games WHERE id = #{id}")
    int delete(Long id);
}