package com.carta.nbaservice.configurations;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.HttpClientErrorException;

import com.carta.nbaservice.services.NbaService;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@EnableConfigurationProperties(value = AppConfig.class)
@TestPropertySource("classpath:invalid-key.properties")
class AppConfigInvalidKeyTest {

    public static final int GAME_ID = 264402;
    public static final String AN_ERROR_HAS_OCCURRED = "403 An error has occurred";

    @Autowired
    private NbaService nbaService;

    @Test
    void givenInvalidKey_whenAccessingNBAApi_thenShouldReturn403() {
        Exception exception = assertThrows(HttpClientErrorException.class, () -> nbaService.fetchGame(GAME_ID));
        String actualMessage = exception.getMessage();

        assertThat(actualMessage, containsString(AN_ERROR_HAS_OCCURRED));
    }

}