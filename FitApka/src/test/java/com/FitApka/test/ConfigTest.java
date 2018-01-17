import java.sql.Connection;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import javax.sql.DataSource;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;

import com.FitApka.Application;



@RunWith(SpringRunner.class)
@SpringBootTest
@DataJpaTest
@WebAppConfiguration
@ContextConfiguration(classes = {Application.class})
@AutoConfigureMockMvc

public abstract class  ConfigTest {
	
	@Autowired
    MockMvc mockMvc;
	
	@Autowired
    private WebApplicationContext context;
	
	@SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    DataSource dataSource;


    final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private static boolean setUpIsDone = false;
    
    @Before
    public void before() throws Exception {
    	
    	  if (setUpIsDone) {
    	        return;
    	    }
    	  
        final Connection connection = dataSource.getConnection();
        final Statement statement = connection.createStatement();
        statement.execute("DROP ALL OBJECTS");
        statement.execute("RUNSCRIPT FROM 'classpath:/TestDB.sql'");
        
        setUpIsDone = true;
    
    }
    
    @Before
	public void setup() {
		mockMvc = MockMvcBuilders
				.webAppContextSetup(context)
				.apply(springSecurity())
				.build();
	}

}

