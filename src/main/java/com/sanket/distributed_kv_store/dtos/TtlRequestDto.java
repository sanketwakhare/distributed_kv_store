package com.sanket.distributed_kv_store.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TtlRequestDto {
    private String key;
    private int seconds;
}
