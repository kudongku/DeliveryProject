package com.sparta.deliveryproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.deliveryproject.config.WebSecurityConfig;
import com.sparta.deliveryproject.entity.User;
import com.sparta.deliveryproject.entity.UserRoleEnum;
import com.sparta.deliveryproject.filter.MockSpringSecurityFilter;
import com.sparta.deliveryproject.requestDto.MenuRequestDto;
import com.sparta.deliveryproject.security.UserDetailsImpl;
import com.sparta.deliveryproject.service.MenuService;
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
@WebMvcTest(controllers = MenuController.class,
        excludeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        classes = WebSecurityConfig.class
                )
        }
)
class MenuControllerTest {

    @Autowired
    ObjectMapper mapper;

    @Autowired
    MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    private Principal principal;

    @MockBean
    private MenuService menuService;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity(new MockSpringSecurityFilter()))
                .build();
    }

    private void mockUserSetup() {
        User user = new User("kudongku@naver.com", "dongku", "1234", "경기", UserRoleEnum.ENTRE);
        UserDetailsImpl userDetails = new UserDetailsImpl(user);
        principal = new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    @Test
    @DisplayName("메뉴 불러오기")
    void getMenuListByStore() throws Exception {
        //then
        mvc.perform(get("/api/public/menus/13?page=1&size=2&sortBy=id&isAsc=true"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("메뉴 top3불러오기")
    void getTopThreeSalesMenuListByStore() throws Exception {
        //then
        mvc.perform(get("/api/menus/13/sales"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("메뉴 top3불러오기2")
    void getTopThreeCountsMenuListByStore() throws Exception {
        //then
        mvc.perform(get("/api/menus/13/counts"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("메뉴 생성하기")
    void createMenu() throws Exception {
        this.mockUserSetup();
        MenuRequestDto menuRequestDto = new MenuRequestDto();
        String body = mapper.writeValueAsString(menuRequestDto);
        //then
        mvc.perform(post("/api/menus/13")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .principal(principal)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("메뉴 수정하기")
    void editMenu() throws Exception {
        this.mockUserSetup();

        MenuRequestDto menuRequestDto = new MenuRequestDto();
        String body = mapper.writeValueAsString(menuRequestDto);

        //then
        mvc.perform(put("/api/menus/1")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .principal(principal)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("메뉴 삭제하기")
    void deleteStore() throws Exception {
        this.mockUserSetup();

        //then
        mvc.perform(delete("/api/menus/1")
                        .principal(principal)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }
}