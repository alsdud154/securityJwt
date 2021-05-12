package kr.co.velnova.securityjwt.controller;

import io.jsonwebtoken.Claims;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

@RestController
public class SecurityJwtController {


    @GetMapping("/test")
    public String create(Authentication authentication) {

        Map<String, Object> claims = (Map<String, Object>) authentication.getPrincipal();

        System.out.println("claims = " + claims);

        return "success";
    }

}
