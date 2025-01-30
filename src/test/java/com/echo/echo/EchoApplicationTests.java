package com.echo.echo;

import static org.mockito.Mockito.mockStatic;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EchoApplicationTests {

	@Test
    void testMainMethodCallsSpringApplicationRun() {
        try (MockedStatic<SpringApplication> mockedStatic = mockStatic(SpringApplication.class)) {
            String[] args = {};
            EchoApplication.main(args);
            mockedStatic.verify(() -> SpringApplication.run(EchoApplication.class, args));
        }
    }

}
