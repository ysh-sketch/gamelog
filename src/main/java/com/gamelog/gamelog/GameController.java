package com.gamelog.gamelog;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

@RestController
public class GameController {
    private final GameMapper gameMapper;

    public GameController(GameMapper gameMapper) {
        this.gameMapper = gameMapper;
    }

    @GetMapping("/games")
    public Result<List<Game>> getGames() {
        List<Game> list = gameMapper.findAll();
        return Result.success(list);
    }

    @PostMapping("/games")
    public Result<Game> addGame(@RequestBody Game game) {
        gameMapper.insert(game);
        Game addedGame = gameMapper.findById(game.getId());
        return Result.success(addedGame);
    }

    @GetMapping("/games/{id}")
    public Result<Game> getGameById(@PathVariable Long id) {
        Game game = gameMapper.findById(id);
        if (game == null) {
            throw new BizException(404, "游戏不存在");
        }
        return Result.success(game);
    }

    @PutMapping("/games/{id}/name")
    public Result<Game> updateGameName(@PathVariable Long id, @RequestBody Game game) {
        int updatedRows = gameMapper.updateName(id, game.getName());
        if (updatedRows == 0) {
            throw new BizException(404, "游戏不存在");
        }
        if (game.getName() == null) {
            throw new BizException(400, "游戏名不能为空");
        }
        Game updatedGame = gameMapper.findById(id);
        return Result.success(updatedGame);
    }

    @PutMapping("/games/{id}/platform")
    public Result<Game> updateGamePlatform(@PathVariable Long id, @RequestBody Game game) {
        int updatedRows = gameMapper.updatePlatform(id, game.getPlatform());
        if (updatedRows == 0) {
            throw new BizException(404, "游戏不存在");
        }
        if (game.getPlatform() == null) {
            throw new BizException(400, "平台不能为空");
        }
        Game updatedGame = gameMapper.findById(id);
        return Result.success(updatedGame);
    }

    @PutMapping("/games/{id}/state")
    public Result<Game> updateGameState(@PathVariable Long id, @RequestBody Game game) {
        int updatedRows = gameMapper.updateState(id, game.getState());
        if (updatedRows == 0) {
            throw new BizException(404, "游戏不存在");
        }
        if (game.getState() == null) {
            throw new BizException(400, "状态不能为空");
        }
        Game updatedGame = gameMapper.findById(id);
        return Result.success(updatedGame);
    }

    @PutMapping("/games/{id}/rating")
    public Result<Game> updateGameRating(@PathVariable Long id, @RequestBody Game game) {
        int updatedRows = gameMapper.updateRating(id, game.getRating());
        if (updatedRows == 0) {
            throw new BizException(404, "游戏不存在");
        }
        if (game.getRating() == null) {
            throw new BizException(400, "评分不能为空");
        }
        Game updatedGame = gameMapper.findById(id);
        return Result.success(updatedGame);
    }

    @PutMapping("/games/{id}/reflection")
    public Result<Game> updateGameReflection(@PathVariable Long id, @RequestBody Game game) {
        int updatedRows = gameMapper.updateReflection(id, game.getReflection());
        if (updatedRows == 0) {
            throw new BizException(404, "游戏不存在");
        }
        if (game.getReflection() == null) {
            throw new BizException(400, "感想不能为空");
        }
        Game updatedGame = gameMapper.findById(id);
        return Result.success(updatedGame);
    }

    @DeleteMapping("/games/{id}")
    public Result<Long> deleteGame(@PathVariable Long id) {
        Game deleted = gameMapper.findById(id);
        if (deleted == null) {
            throw new BizException(404, "游戏不存在");
        }
        gameMapper.delete(id);
        return Result.success(id);
    }
}