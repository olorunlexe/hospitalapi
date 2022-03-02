package com.assessment.hospitalapi.controller;

import com.assessment.hospitalapi.HospitalapiApplication;
import com.assessment.hospitalapi.entities.Staff;
import com.assessment.hospitalapi.helpers.GenericResponse;
import com.assessment.hospitalapi.model.CreateStaffRequest;
import com.assessment.hospitalapi.model.UpdateStaffRequest;
import com.assessment.hospitalapi.services.ManagementService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(HospitalapiApplication.class)
public class StaffControllerTest {
    @MockBean
    StaffController staffController;

    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void should_return_created_staff() throws Exception {
        CreateStaffRequest request = new CreateStaffRequest();
        request.setName("joel");

        Staff staff = new Staff();
        staff.setName(request.getName());

        Map<String, Object> map = mapper.convertValue(request, Map.class);

        GenericResponse response = GenericResponse.generic200Response(staff);
        when(staffController.createStaff(any(CreateStaffRequest.class))).thenReturn(ResponseEntity.status(response.getCode()).body(response));

        mockMvc.perform(post("/api/v1/staff")
                        .content(mapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name", Matchers.equalTo(request.getName())));
    }

    @Test
    public void should_return_updated_staff() throws Exception {
        UpdateStaffRequest request = new UpdateStaffRequest();
        request.setUpdateName("joel1");
        request.setUuid("f240fe4b-2846-45f0-8dbf-f44c1fc69d91");

        Staff staff = new Staff();
        staff.setName(request.getUpdateName());
        staff.setUuid(request.getUuid());

        Map<String, Object> map = mapper.convertValue(request, Map.class);

        GenericResponse response = GenericResponse.generic200Response(staff);
        when(staffController.updateStaff(any(UpdateStaffRequest.class))).thenReturn(ResponseEntity.status(response.getCode()).body(response));

        mockMvc.perform(put("/api/v1/staff")
                        .content(mapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name", Matchers.equalTo(request.getUpdateName())))
                .andExpect(jsonPath("$.data.uuid", Matchers.equalTo(request.getUuid())));
    }

}