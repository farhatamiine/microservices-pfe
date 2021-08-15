package com.abdali.microhps.devicemerchantservice.controller;
 
import java.util.Arrays; 
import java.util.Map; 

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType; 
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.abdali.microhps.devicemerchantservice.model.Device;
import com.abdali.microhps.devicemerchantservice.service.impl.DeviceServiceImpl; 

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
 
@WebMvcTest(DeviceController.class) 
public class DeviceControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private DeviceServiceImpl deviceService;
	
	@Test
	public void basicTest() throws Exception {
		
		RequestBuilder request = MockMvcRequestBuilders
				.get("/basic")
				.accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(request)
				.andExpect(status().isOk())
				.andExpect(content().json("hello world"))
				.andReturn();
		//JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
		
	}
	
	@Test
	@DisplayName("Test findAll Devices")
	public void findAllDevices_InputsAreValid_ReturnDevicesList() throws Exception {
      when(deviceService.findAll(Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt())).thenReturn((Map<String, Object>) Arrays.asList(new Device(), new Device())
				);
      mockMvc.perform(get("/merchant-device/devices")
              .accept(MediaType.APPLICATION_JSON))
              .andExpect(status().isOk());
      
      verify(deviceService,times(1)).findAll(Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt());
	}
}
