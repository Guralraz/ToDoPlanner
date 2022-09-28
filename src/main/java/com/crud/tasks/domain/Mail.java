package com.crud.tasks.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@Getter
@RequiredArgsConstructor
public class Mail {
    private final String mailTo;
    private final String toCc;
    private final String subject;
    private final String message;
}
