# Design Distributed KV Store using relational DB

### Requirements Gathering
1. Design a Key-value store using relational database.
2. The store should support below operations.
3. The keys should be auto deleted upon expiration.
4. The store should handle 1 million concurrent requests.
---

### Non Functions Requirements

---

### Database Setup
    create schema 'kv_store'

---

### Entities
1. StoreEntry
            
        uuid: varchar | 
        s_key: varchar | 
        s_value: varchar | 
        ttl: bigint

---

### Relationships

    NA

---

### Resources:
1. Arpit Bhayani's system design problem

   https://github.com/arpitbbhayani/system-design-questions/blob/master/sql-kv.md
