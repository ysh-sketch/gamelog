package com.gamelog.gamelog;

import java.time.OffsetDateTime;

public record CommentResponse(Long id, String username, String content, OffsetDateTime createdAt) {

}
