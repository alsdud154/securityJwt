package kr.co.velnova.securityjwt.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserController {

    // 로그인
    @GetMapping("/user/test")
    public String user(Authentication authentication) {

        System.out.println("authentication = " + authentication.getPrincipal().toString());

        return "user test";
    }

    // 로그인
    @GetMapping("/admin/test")
    public String admin(Authentication authentication) {

        System.out.println("authentication = " + authentication.getPrincipal());

        return "admin test";
    }

    // 로그인
    @GetMapping("/super/test")
    public String supers(Authentication authentication) {

        System.out.println("authentication = " + authentication.getPrincipal());

        return "super test";
    }
}