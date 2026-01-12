package com.lxp.auth.domain.local.service.spec;

import java.util.List;

public record UserSpec(
    String email,
    String name,
    String role,
    List<Long> tags,
    String level,
    String job
) {
}
