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
public interface CommentMapper {

    @Insert("INSERT INTO comments (user_id, game_id, content) VALUES (#{userId}, #{gameId}, #{content})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Comment comment);

    @Select("SELECT c.id, u.username, c.content, c.created_at FROM comments c JOIN users u ON c.user_id = u.id WHERE c.game_id = #{gameId}")
    List<CommentResponse> findByGameIdWithUser(Long gameId);

    @Select("SELECT id, user_id, game_id, content, created_at FROM comments WHERE id = #{id}")
    Comment findById(Long id);

    @Delete("DELETE FROM comments WHERE id = #{id}")
    int delete(Long id);
}
