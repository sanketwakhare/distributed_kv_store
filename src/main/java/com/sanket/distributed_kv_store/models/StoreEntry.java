package com.sanket.distributed_kv_store.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Getter
@Setter
@Table(name = "store")
public class StoreEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    @Column(name = "uuid")
    private String id;

    @Column(name = "s_key", unique = true, nullable = false)
    private String key;

    @Column(name = "s_value")
    private String value;

    @Column(name = "ttl")
    private Long ttl;

}
