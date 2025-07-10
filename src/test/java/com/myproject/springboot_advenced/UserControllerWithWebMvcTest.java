package com.myproject.springboot_advenced;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@Slf4j
@WebMvcTest
class UserControllerWithWebMvcTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    @Autowired
    ObjectMapper objectMapper;


    @Test
    void create() throws Exception {
        User user = User.builder()
                .email("test@test.com")
                .userName("test")
                .password("123")
                .otp("123")
                .build();
        mockMvc.perform(MockMvcRequestBuilders.post("/api/users")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
        verify(userService, times(1)).create(user);
    }

    @Test
    void createWithSuccessfullyResponse() throws Exception {
        User user = User.builder()
                .email("test@test.com")
                .userName("test")
                .password("123")
                .otp("123")
                .build();
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/users")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();
        //ModelAndView modelAndView = mvcResult.getModelAndView();
        MockHttpServletResponse response = mvcResult.getResponse();
        String contentAsString = response.getContentAsString();
        User returnedUser = objectMapper.readValue(contentAsString, User.class);
        assertEquals(1, returnedUser.getId());
        verify(userService, times(1)).create(user);
    }

    @Test
    void createWithErrorResponse() throws Exception {
        User user = User.builder()
                .password("test123")
                .otp("123")
                .build();
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/users")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        log.info(contentAsString);
        AppErrorDto errorDto = objectMapper.readValue(contentAsString, AppErrorDto.class);
        assertEquals("Invalid input", errorDto.getFriendlyMessage());
        assertEquals("/api/users", errorDto.getErrorPath());
        assertEquals("400", errorDto.getErrorCode());
    }


}