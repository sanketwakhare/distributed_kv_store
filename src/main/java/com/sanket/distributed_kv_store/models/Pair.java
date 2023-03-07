package com.sanket.distributed_kv_store.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Pair {
    private String key;
    private String value;
}
