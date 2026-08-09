package br.com.barbershop.api;
import br.com.barbershop.repository.*;
import br.com.barbershop.service.SchedulingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@WebMvcTest(BarbershopController.class) class BookingValidationTest{
 @Autowired MockMvc mvc;@MockBean ProfessionalRepository professionals;@MockBean BarberServiceRepository services;@MockBean SchedulingService scheduling;
 @Test void rejectsInvalidBookingPayload()throws Exception{mvc.perform(post("/api/bookings").contentType("application/json").content("{\"clientName\":\"\",\"clientEmail\":\"invalid\"}")).andExpect(status().isBadRequest());}
}
