package com.it.yanxuan.seller.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 处理登录用户
 * @author aaaa
 */
@RestController
@RequestMapping("/loginUser")
public class LoginUserController {

    /**
     * 获取springSecurity中的登录用户名
     * @return
     */
    @GetMapping
    public ResponseEntity<Map> getLoginUsername() {
        Map<String, String> map = new HashMap<>(3);
        //从SpringSecurity中获取登录的用户名
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        map.put("username", username);

        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
