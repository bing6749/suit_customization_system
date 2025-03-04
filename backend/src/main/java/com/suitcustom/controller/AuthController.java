package com.suitcustom.controller;

import com.suitcustom.common.R;
import com.suitcustom.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 认证控制器
 * 
 * @author suitcustom
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  @PostMapping("/login")
  public R<?> login(@RequestBody Map<String, String> loginRequest) {
    String username = loginRequest.get("username");
    String password = loginRequest.get("password");

    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(username, password));

    final UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    final String token = jwtTokenUtil.generateToken(userDetails);

    Map<String, Object> response = new HashMap<>();
    response.put("token", token);
    response.put("username", userDetails.getUsername());

    return R.ok(response);
  }
}