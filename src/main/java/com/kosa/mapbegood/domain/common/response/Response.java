package com.kosa.mapbegood.domain.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class Response implements Serializable {
    private int status;
    private String message;
}