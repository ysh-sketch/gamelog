package com.gamelog.gamelog;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gamelog.gamelog.CommentMapper;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

@RestController
public class CommentController {
    private final CommentMapper commentMapper;

    public CommentController(CommentMapper commentMapper) {
        this.commentMapper = commentMapper;
    }

    @PostMapping("/comments")
    public Result<Comment> addComment(@RequestBody Comment comment) {
        commentMapper.insert(comment);
        Comment addedComment = commentMapper.findById(comment.getId());
        return Result.success(addedComment);
    }

    @GetMapping("/comments/{gameId}")
    public Result<List<CommentResponse>> findByGameId(@PathVariable Long gameId) {
        List<CommentResponse> comments = commentMapper.findByGameIdWithUser(gameId);
        return Result.success(comments);
    }

    @DeleteMapping("/comments/{id}")
    public Result<Long> deleteComment(@PathVariable Long id) {
        int deletedRows = commentMapper.delete(id);
        if (deletedRows == 0) {
            throw new BizException(404, "评论不存在");
        }
        return Result.success(id);
    }
}