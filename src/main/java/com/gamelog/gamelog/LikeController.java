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
public class LikeController {
    private final LikeMapper likeMapper;

    public LikeController(LikeMapper likeMapper) {
        this.likeMapper = likeMapper;
    }

    @PostMapping("/likes")
    public Result<Like> addLike(@RequestBody Like like) {
        int rows = likeMapper.insert(like);
        if (rows == 0) {
            throw new BizException(409, "已经点过赞了");
        }
        Like savedLike = likeMapper.findById(like.getUserId(), like.getGameId());
        return Result.success(savedLike);
    }

    @DeleteMapping("/likes/{userId}/{gameId}")
    public Result<Long> deleteLike(@PathVariable Long userId, @PathVariable Long gameId) {
        int deletedRows = likeMapper.delete(userId, gameId);
        if (deletedRows == 0) {
            throw new BizException(404, "点赞不存在");
        }
        return Result.success(gameId);
    }
}