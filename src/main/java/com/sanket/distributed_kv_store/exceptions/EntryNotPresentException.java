package com.sanket.distributed_kv_store.exceptions;

public class EntryNotPresentException extends Throwable {
    public EntryNotPresentException(String key) {
        super("entry not present with key : " + key);
    }
}
