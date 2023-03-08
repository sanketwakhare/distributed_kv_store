package com.sanket.distributed_kv_store.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Error {
    private String errorMessage;

    public Error(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
