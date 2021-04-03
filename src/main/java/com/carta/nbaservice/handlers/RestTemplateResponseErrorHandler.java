package com.carta.nbaservice.handlers;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResponseErrorHandler;

public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {

    public static final String SERVER_ERROR_MESSAGE = "An error has occurred on Free NBA API. Please contact RapidAPI for more information.";
    public static final String GAME_ID_NOT_FOUND = "Game ID not found on Free NBA API";
    public static final String CLIENT_ERROR_MESSAGE = "An error has occurred upon requesting data to Free NBA API. Please contact your system administrator.";

    @Override
    public boolean hasError(ClientHttpResponse clientHttpResponse) throws IOException {
        return (clientHttpResponse.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR
                || clientHttpResponse.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR);
    }

    @Override
    public void handleError(ClientHttpResponse clientHttpResponse) throws IOException {
        if (clientHttpResponse.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR) {
            throw new HttpServerErrorException(clientHttpResponse.getStatusCode(), SERVER_ERROR_MESSAGE);
        } else if (clientHttpResponse.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR) {
            if (clientHttpResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new HttpClientErrorException(clientHttpResponse.getStatusCode(), GAME_ID_NOT_FOUND);
            } else {
                throw new HttpClientErrorException(clientHttpResponse.getStatusCode(), CLIENT_ERROR_MESSAGE);
            }
        }
    }
}
