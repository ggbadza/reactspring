package com.tankmilu.spring.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tankmilu.spring.dto.UserLoginDto;
import com.tankmilu.spring.dto.UserModifyDto;
import com.tankmilu.spring.dto.UserRegisterDto;
import com.tankmilu.spring.security.CustomUserDetails;
import com.tankmilu.spring.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {
    
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UserRegisterDto userRequest) {
        var data = userService.register(userRequest);
        return ResponseEntity.ok().body(data);
    }

    // 유저 리스트 Get
    @Secured("ROLE_ADMIN") 
    @GetMapping("/userlist")
    public ResponseEntity<?> userList() {
        var data = userService.findAll();
        return ResponseEntity.ok().body(data);
    }

    // 유저 정보 Get
    @Secured("ROLE_ADMIN") 
    @GetMapping("/getuser")
    public ResponseEntity<?> getUser(@RequestParam(value="uid") int uid) {
        var data = userService.findUser(uid);
        return ResponseEntity.ok().body(data);
    }

    // 본인 설정만 수정
    @PutMapping("/modify")
    public ResponseEntity<?> modify(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody UserModifyDto userRequest) {
        var data = userService.modifyUser(userRequest,userDetails.getUid());
        return ResponseEntity.ok().body(data);
    }

    // 모든 유저 설정 수정 by 어드민
    @Secured("ROLE_ADMIN") 
    @PutMapping("/modifybyadmin")
    public ResponseEntity<?> modifyByAdmin(@RequestBody UserModifyDto userRequest) {
        var data = userService.modifyUserByAdmin(userRequest);
        return ResponseEntity.ok().body(data);
    }

    @PostMapping("/login")
    public String login(@RequestBody UserLoginDto userLoginDto, HttpServletResponse response) {
        userService.login(userLoginDto, response);
        return "success!";
    }

    @GetMapping("/info") // 인증된 사용자인지 확인
    public ResponseEntity<?> getUserName(@AuthenticationPrincipal CustomUserDetails userDetails) {
        var data = userService.findUser(userDetails.getEmail());
        return ResponseEntity.ok().body(data);
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteUser(@AuthenticationPrincipal CustomUserDetails userDetails) {
        var data = userService.deleteUser(userDetails.getUid());
        return ResponseEntity.ok().body(data);
    }

    @GetMapping("/test")
    public String test() {
        return "success!";
    }

    // public ResponseEntity<?> Login(@RequestBody UserLoginDto userRequest) {
    //     var data = userService.register(userRequest);
    //     return ResponseEntity.ok().body(data);
    // }


    @PostMapping("/oauthlogin")
    public String oauthLogin(@RequestParam String code, @RequestParam("type") String type, HttpServletResponse response) throws JsonProcessingException {
        if(type.equals("kakao")) {
            String createToken = userService.kakaoToken(code,"http://localhost:3000/oauthloginproc?type=kakao");
            userService.kakaoLogin(createToken, response);
        } else if(type.equals("naver")) {
            String createToken = userService.naverToken(code,"http://localhost:3000/oauthloginproc?type=kakao");
            userService.naverLogin(createToken, response);
        } else {
            return type;
        }

        return "success!";
    }

    // @PutMapping("/registernaver")
    // public ResponseEntity<?> registerNaver(@RequestParam String code,  @AuthenticationPrincipal CustomUserDetails userDetails) {

    //     return ResponseEntity.ok().body(data);
    // }

    @PutMapping("/registeroauth")
    public String registerOauth (@RequestParam String code, @RequestParam String type, @AuthenticationPrincipal CustomUserDetails userDetails) throws JsonProcessingException {
        if (type.equals("kakao")) {
            String createToken = userService.kakaoToken(code,"http://localhost:3000/oauthregister?type=kakao");
            userService.registerKakao(userDetails.getUid(),createToken);
        }else if(type.equals("naver")) {
            String createToken = userService.naverToken(code,"http://localhost:3000/oauthregister?type=naver");
            userService.registerNaver(userDetails.getUid(),createToken);
        } else {
            return "false!";
        }
        return "success!";
    }

    

}
