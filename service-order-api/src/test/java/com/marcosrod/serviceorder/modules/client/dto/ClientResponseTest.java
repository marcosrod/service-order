package com.marcosrod.serviceorder.modules.client.dto;

import org.junit.jupiter.api.Test;

import static com.marcosrod.serviceorder.modules.client.helper.ClientHelper.getClientResponse;
import static com.marcosrod.serviceorder.modules.client.helper.ClientHelper.getSavedClient;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClientResponseTest {

    @Test
    void of_shouldReturnClientResponse_whenRequested() {
        var expectedResponse = getClientResponse();

        assertEquals(expectedResponse, ClientResponse.of(getSavedClient()));
    }
}
