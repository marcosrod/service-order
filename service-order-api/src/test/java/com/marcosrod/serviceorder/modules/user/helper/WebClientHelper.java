package com.marcosrod.serviceorder.modules.user.helper;

import okhttp3.mockwebserver.MockResponse;
import org.springframework.http.MediaType;

import java.util.List;

import static com.marcosrod.serviceorder.modules.common.helper.ConstantUtil.TEST_ID_ONE;
import static com.marcosrod.serviceorder.modules.order.helper.OrderHelper.TEST_ID_TWO;

public class WebClientHelper {

    public static MockResponse getMockResponse(String body) {
        return new MockResponse()
                .setBody(body)
                .addHeader("Content-Type", MediaType.APPLICATION_JSON);
    }

    public static List<Long> getUserIds() {
        return List.of(TEST_ID_ONE, TEST_ID_TWO);
    }
}
