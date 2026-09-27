package com.gamelog.gamelog;

import java.time.OffsetDateTime;

public record UserResponse(Long id, String username, OffsetDateTime createdAt) {
    

}