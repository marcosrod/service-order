package com.marcosrod.serviceorder.modules.client.helper;

import com.marcosrod.serviceorder.modules.client.dto.ClientRequest;
import com.marcosrod.serviceorder.modules.client.dto.ClientResponse;
import com.marcosrod.serviceorder.modules.client.filter.ClientFilter;
import com.marcosrod.serviceorder.modules.client.model.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Collections;

import static com.marcosrod.serviceorder.modules.common.helper.ConstantUtil.TEST_ID_ONE;
import static com.marcosrod.serviceorder.modules.order.helper.OrderHelper.getPageable;

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

    public static Page<ClientResponse> getClientResponsePage() {
        return new PageImpl<>(Collections.singletonList(getClientResponse()), getPageable(), TEST_ID_ONE);
    }

    public static ClientFilter getClientFilter() {
        var filter = new ClientFilter();
        filter.setId(TEST_ID_ONE);

        return filter;
    }
}
