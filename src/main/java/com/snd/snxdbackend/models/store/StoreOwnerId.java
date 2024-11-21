package com.snd.snxdbackend.models.store;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serial;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class StoreOwnerId implements java.io.Serializable {
    @Serial
    private static final long serialVersionUID = -455730580768909383L;
    @Column(name = "store_id", nullable = false)
    private Integer storeId;

    @Column(name = "owner_id", nullable = false)
    private Integer ownerId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        StoreOwnerId entity = (StoreOwnerId) o;
        return Objects.equals(this.storeId, entity.storeId) &&
                Objects.equals(this.ownerId, entity.ownerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(storeId, ownerId);
    }

}