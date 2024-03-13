package com.sparta.deliveryproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.deliveryproject.config.WebSecurityConfig;
import com.sparta.deliveryproject.entity.User;
import com.sparta.deliveryproject.entity.UserRoleEnum;
import com.sparta.deliveryproject.filter.MockSpringSecurityFilter;
import com.sparta.deliveryproject.requestDto.CategoryRequestDto;
import com.sparta.deliveryproject.security.UserDetailsImpl;
import com.sparta.deliveryproject.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.security.Principal;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest(controllers = CategoryController.class,
        excludeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        classes = WebSecurityConfig.class
                )
        }
)
class CategoryControllerTest {

    @Autowired
    ObjectMapper mapper;

    @Autowired
    MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    private Principal principal;

    @MockBean
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity(new MockSpringSecurityFilter()))
                .build();
    }

    private void mockUserSetup() {
        User user = new User("kudongku@naver.com", "dongku", "1234", "경기", UserRoleEnum.ADMIN);
        UserDetailsImpl userDetails = new UserDetailsImpl(user);
        principal = new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    @Test
    @DisplayName("카테고리 불러오기")
    void getCategory() throws Exception {
        //then
        mvc.perform(get("/api/public/categories"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("카테고리 작성하기")
    void postCategory() throws Exception {
        this.mockUserSetup();

        String name = "JAPANESE";
        String introduce = "일식";
        String body = mapper.writeValueAsString(new CategoryRequestDto(name, introduce));

        //then
        mvc.perform(post("/api/categories")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .principal(principal)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("카테고리 수정하기")
    void editCategory() throws Exception {
        this.mockUserSetup();

        String name = "JAPANESE";
        String introduce = "일식";
        String body = mapper.writeValueAsString(new CategoryRequestDto(name, introduce));

        //then
        mvc.perform(put("/api/categories/1")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .principal(principal)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("카테고리 삭하기")
    void deleteCategory() throws Exception {
        this.mockUserSetup();

        //then
        mvc.perform(delete("/api/categories/1")
                        .principal(principal)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }
}