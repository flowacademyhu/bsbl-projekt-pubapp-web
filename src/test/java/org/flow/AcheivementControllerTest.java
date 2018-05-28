package org.flow;

import org.flow.controllers.AchievementController;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AcheivementControllerTest {

    @Mock
    private AchievementController achievementController;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(achievementController).build();
    }

    @Test
    public void getAchievement() throws Exception {
        this.mockMvc.perform(get("/achievement/{id}", 1))
                .andExpect(status().isOk());
    }
}
