import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@AutoConfigureCache
@AutoConfigureWebMvc
@AutoConfigureMockMvc
public class ControllerTest extends ConfigTest   {
	

	@Autowired
    MockMvc mockMvc;
	
	@Test
	@WithMockUser(username = "admin", authorities = { "ADMIN", "USER" })
    public void testCreateClientSuccessfully() throws Exception {
		mockMvc.perform(get("/profile2").accept(MediaType.TEXT_PLAIN)).andExpect(content().string("artur"));
			     
    }

}
