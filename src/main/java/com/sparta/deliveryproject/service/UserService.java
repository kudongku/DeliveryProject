package com.sparta.deliveryproject.service;



import com.sparta.deliveryproject.dto.LoginRequestDto;
import com.sparta.deliveryproject.dto.SignupRequestDto;
import com.sparta.deliveryproject.entity.User;
import com.sparta.deliveryproject.entity.UserRoleEnum;
import com.sparta.deliveryproject.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service

public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

private final JwtUtil jwtUtil;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }
    String ADMIN_TOKEN = "2";
    String ENTRE_TOKEN ="6";
    public void signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());
        String address = requestDto.getAddress();
        // 회원 중복 확인
        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        UserRoleEnum role = UserRoleEnum.USER;


            if (ADMIN_TOKEN.equals(requestDto.getAuthorityToken())) {

                role = UserRoleEnum.ADMIN;
            }
            else if(ENTRE_TOKEN.equals(requestDto.getAuthorityToken())){

                role = UserRoleEnum.ENTRE;
            }


        User user = new User(username,password,address,role);
        userRepository.save(user);
    }

   public void login(LoginRequestDto requestDto, HttpServletResponse res) {
         String username = requestDto.getUsername();
         String password = requestDto.getPassword();
logger.info(username);
        logger.info(password);

         User user = userRepository.findByUsername(username).orElseThrow(
                 () -> new IllegalArgumentException("등록된 사용자가 없습니다")

         );
       UserRoleEnum role = user.getRole();

         if(!passwordEncoder.matches(password,user.getPassword())){
             throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");

         }
         String token = jwtUtil.createToken(user.getUsername(),role);
        jwtUtil.addJwtToCookie(token, res);

    }


}
