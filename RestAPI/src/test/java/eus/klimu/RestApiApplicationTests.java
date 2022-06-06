package eus.klimu;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class RestApiApplicationTests {

    @Test
    void contextLoads() {
        RestApiApplication restApiApplication = new RestApiApplication();
        assertThat(restApiApplication.passwordEncoder()).isNotNull();
    }

}
