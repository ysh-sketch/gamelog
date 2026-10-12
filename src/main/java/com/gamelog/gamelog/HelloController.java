package com.gamelog.gamelog;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@RestController
public class HelloController {
    private final UserMapper userMapper;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder;

    public HelloController(UserMapper userMapper, JwtUtil jwtUtil) {
        this.userMapper = userMapper;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @PostMapping("/users")
    public Result<UserResponse> register(@RequestBody User user) {
        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
        userMapper.insert(user);
        User registeredUser = userMapper.findById(user.getId());
        return Result.success(
                new UserResponse(registeredUser.getId(), registeredUser.getUsername(), registeredUser.getCreatedAt()));
    }

    @PostMapping("/login")
    public Result<String> login(@RequestBody LoginRequest request) {
        User user = userMapper.findByUsername(request.username());
        if (user == null || !passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            throw new BizException(401, "用户名或密码错误");
        }
        String token = jwtUtil.generate(user.getId());
        return Result.success(token);
    }

    @GetMapping("/users")
    public Result<List<UserResponse>> getUsers() {
        List<UserResponse> list = userMapper.findAll().stream()
                .map(user -> new UserResponse(user.getId(), user.getUsername(), user.getCreatedAt()))
                .toList();
        return Result.success(list); // 整个 List 作为一个对象装进信封
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