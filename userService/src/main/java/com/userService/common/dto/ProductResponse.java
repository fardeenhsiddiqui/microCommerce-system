package com.userService.common.dto;

import java.util.UUID;

public record ProductResponse(
        UUID id,
        String name,
        String desc,
        Double price,
        String categoryName
) {

}
