package com.ead.authUser.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorRecordResponse(int statusCode, String errorMessage, Map<String, String> errorsDetails) {
}
