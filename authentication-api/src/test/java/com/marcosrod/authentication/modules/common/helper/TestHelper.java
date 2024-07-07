package com.marcosrod.authentication.modules.common.helper;

import org.springframework.data.domain.*;

import static com.marcosrod.authentication.modules.common.helper.ConstantUtil.ID_ATTRIBUTE;

public class TestHelper {

    public static Pageable getPageable() {
        return PageRequest.of(0, 10, Sort.by(ID_ATTRIBUTE));
    }
}
