package nl;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LibControllerTest {

	private static final String PREFIX = "/nl";
    @Autowired
    private MockMvc mvc;

    @Test
    public void getHello() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(PREFIX + "/").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}"));
        //{\"id\":\"8fc390b6-ec37-4487-9ae4-662a85be55cc\",\"content\":\"Greetings from Spring Boot!\"}"
    }
    
    @Test
    public void lent() throws Exception {
    	mvc.perform(MockMvcRequestBuilders.get(PREFIX + "/loaddata").accept(MediaType.TEXT_PLAIN))
        .andExpect(status().isOk())
        .andExpect(content().string(equalTo("DONE")));

    	mvc.perform(MockMvcRequestBuilders.get(PREFIX + "/books").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
//        .andExpect(content().json("[]"));

    	mvc.perform(MockMvcRequestBuilders.get(PREFIX + "/addperson?name=test&emailAddress=xx@xx.xx&phoneNumber=1234567").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string("1"));
    	
    	mvc.perform(MockMvcRequestBuilders.get(PREFIX + "/persons").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().json("[{}]"));
    	//[{"id":1,"name":"test","phoneNumber":"1234567","emailAddress":"xx@xx.xx","books":[]}]
    	//[{"id":1,"name":"test","phoneNumber":"1234567","emailAddress":"xx@xx.xx"}]
    	
    	mvc.perform(MockMvcRequestBuilders.get(PREFIX + "/lend/1/1").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string("true"));
    	
    	mvc.perform(MockMvcRequestBuilders.get(PREFIX + "/booklent/1").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().json("[{}]"));
    	//[{"id":1,"isbn":"1-3456-8901-0","author":"Jules Verne","title":"Journey to the Center of the Earth"}]
    }
}
