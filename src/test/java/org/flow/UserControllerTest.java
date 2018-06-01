package org.flow;

import org.flow.controllers.UserController;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest {

    @Mock
    private UserController userController;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }
    /*
    @Test
    public void getUser() throws Exception {
        this.mockMvc.perform(get("/users/{id}", 1))
                .andExpect(status().isOk());
    }


    @Test
    public void createUser() throws Exception {
        this.mockMvc.perform(post("/users")
                .param("firstName", "John")
                .param("lastName", "Doe")
                .param("password", "123456")
                .param("nickName", "TheLegend27")
                .param("email", "john@mail.com")
                .param("dob", "1980-01-01")
                .param("gender", "true"))
                .andExpect(status().isOk());
    }
    */
}
