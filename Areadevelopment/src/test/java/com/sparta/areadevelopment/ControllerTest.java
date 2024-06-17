package com.sparta.areadevelopment;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.areadevelopment.Mock.MockSecurityFilter;
import com.sparta.areadevelopment.config.SecurityConfig;
import com.sparta.areadevelopment.controller.AuthController;
import com.sparta.areadevelopment.controller.BoardController;
import com.sparta.areadevelopment.controller.CommentController;
import com.sparta.areadevelopment.controller.UserController;
import com.sparta.areadevelopment.dto.SignupRequestDto;
import com.sparta.areadevelopment.dto.UserLoginDto;
import com.sparta.areadevelopment.entity.CustomUserDetails;
import com.sparta.areadevelopment.entity.User;
import com.sparta.areadevelopment.jwt.TokenProvider;
import com.sparta.areadevelopment.repository.UserRepository;
import com.sparta.areadevelopment.service.AuthService;
import com.sparta.areadevelopment.service.BoardService;
import com.sparta.areadevelopment.service.CommentService;
import com.sparta.areadevelopment.service.CustomUserDetailsService;
import com.sparta.areadevelopment.service.LikeService;
import com.sparta.areadevelopment.service.UserService;
import java.security.Principal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest(
        controllers = {UserController.class, BoardController.class, CommentController.class,
                AuthController.class},
        excludeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        classes = SecurityConfig.class
                )
        }
)
@ExtendWith(MockitoExtension.class)
public class ControllerTest {
    private MockMvc mockMvc;
    private Principal principal;

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private BoardService boardService;
    @MockBean
    private CommentService commentService;
    @MockBean
    private AuthService authService;
    @MockBean
    private UserService userService;
    @MockBean
    private LikeService likeService;
    @MockBean
    private CustomUserDetailsService customUserDetailsService;
    @MockBean
    private TokenProvider tokenProvider;
    @MockBean
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity(new MockSecurityFilter()))
                .build();
    }
   private void mockUser(){
        String username = "testtest1234";
        String password = "testtest1234!";
        String email = "test@test.com";
        String nickname = "testNick";
        String info = "test info";
        User user = new User(username, nickname , password, email, info);
        CustomUserDetails userDetails = new CustomUserDetails(user);
        principal = new UsernamePasswordAuthenticationToken(userDetails,"",userDetails.getAuthorities());
    }
    @Test
    @WithMockUser(username ="testtest1234",password = "testtest1234!")
    @DisplayName("Login Test")
    void testLogin() throws Exception {
        //given
        mockUser();
        UserLoginDto loginDto = new UserLoginDto();
        loginDto.setUsername("testtest1234");
        loginDto.setPassword("testtest1234!");
        //when - then
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content((objectMapper.writeValueAsBytes(loginDto))))
                .andExpect(status().isOk())
                .andDo(print());

    }
    @Test
    @WithMockUser
    @DisplayName("회원가입")
    void testRegister() throws Exception {
        //given
        mockUser();
        SignupRequestDto signupReques = new SignupRequestDto();
        signupReques.setUsername("testtest1234");
        signupReques.setPassword("testtest1234!");
        signupReques.setEmail("test@test.com");
        signupReques.setNickname("testNick");
        signupReques.setInfo("test info");

        when(userRepository.save(any())).thenReturn(new User());
        //when -then
        mockMvc.perform(post("/api/users/sign-up")
                        .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(signupReques)))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
