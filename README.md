# Design Distributed KV Store using relational DB

### Requirements Gathering

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
