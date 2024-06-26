package com.marcosrod.serviceorder.modules.client.helper;

import com.marcosrod.serviceorder.modules.client.dto.ClientRequest;
import com.marcosrod.serviceorder.modules.client.dto.ClientResponse;
import com.marcosrod.serviceorder.modules.client.model.Client;

import static com.marcosrod.serviceorder.modules.common.helper.ConstantUtil.TEST_ID_ONE;

public class ClientHelper {

    private static final String TEST_NAME = "testName";
    private static final String TEST_ADDRESS = "testAddress";
    private static final String TEST_PHONE = "123456789";
    private static final String TEST_EMAIL = "test@email.com";

    public static ClientRequest getClientRequest() {
        return new ClientRequest(TEST_NAME, TEST_ADDRESS, TEST_PHONE, TEST_EMAIL);
    }

    public static Client getClient() {
        return new Client(TEST_NAME, TEST_ADDRESS, TEST_PHONE, TEST_EMAIL);
    }

    public static Client getSavedClient() {
        return new Client(TEST_ID_ONE, TEST_NAME, TEST_ADDRESS, TEST_PHONE, TEST_EMAIL);
    }

    public static ClientResponse getClientResponse() {
        return new ClientResponse(TEST_ID_ONE, TEST_NAME, TEST_ADDRESS, TEST_PHONE, TEST_EMAIL);
    }
}
