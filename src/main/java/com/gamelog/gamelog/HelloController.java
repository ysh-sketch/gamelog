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
public class HelloController {
    private final UserMapper userMapper;

    public HelloController(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @GetMapping("/users")
    public Result<List<UserResponse>> getUsers() {
        List<UserResponse> list = userMapper.findAll().stream()
                .map(user -> new UserResponse(user.getId(), user.getUsername(), user.getCreatedAt()))
                .toList();
        return Result.success(list); // 整个 List 作为一个对象装进信封
    }

    @PostMapping("/users")
    public Result<UserResponse> register(@RequestBody User user) {
        userMapper.insert(user);
        User registeredUser = userMapper.findById(user.getId());
        return Result.success(new UserResponse(registeredUser.getId(), registeredUser.getUsername(), registeredUser.getCreatedAt()));
    }

    @GetMapping("/users/{id}")
    public Result<UserResponse> getUserById(@PathVariable Long id) {
        User user = userMapper.findById(id);
        if (user == null) {
            throw new BizException(404, "用户不存在");
        }
        return Result.success(new UserResponse(user.getId(), user.getUsername(), user.getCreatedAt()));
    }

    @PutMapping("/users/{id}")
    public Result<UserResponse> updateUser(@PathVariable Long id, @RequestBody User user) {
        userMapper.updateUsername(id, user.getUsername());
        User updated = userMapper.findById(id);
        if (updated == null) {
            throw new BizException(404, "用户不存在");
        }
        return Result.success(new UserResponse(updated.getId(), updated.getUsername(), updated.getCreatedAt()));
    }

    @DeleteMapping("/users/{id}")
    public Result<Long> deleteUser(@PathVariable Long id) {
        User deleted = userMapper.findById(id);
        if (deleted == null) {
            throw new BizException(404, "用户不存在");
        }
        userMapper.delete(id);
        return Result.success(deleted.getId());
    }
}