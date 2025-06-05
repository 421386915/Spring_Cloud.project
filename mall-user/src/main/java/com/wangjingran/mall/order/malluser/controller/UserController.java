package com.wangjingran.mall.order.malluser.controller;

import com.wangjingran.mallcommon.result.Result;
import com.wangjingran.mall.order.malluser.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return Result.success(userService.getUserById(id));

    }
    @PostMapping
    public Result<Void> saveUser(@RequestBody User user) {
        userService.save(user);
        return Result.success();
    }
    @PutMapping("/{id}")
    public Result<Void> updateUser(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);
        userService.updateById(user);
        return Result.success();
    }
    @DeleteMapping("/{id}")
    public Result<Void> deleteUser(@PathVariable Long id) {
        userService.removeById(id);
        return Result.success();
    }
}
