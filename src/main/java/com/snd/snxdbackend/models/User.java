package com.snd.snxdbackend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.snd.snxdbackend.enums.UserRole;
import com.snd.snxdbackend.models.store.Store;
import com.snd.snxdbackend.models.wishlist.Wishlist;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.locationtech.jts.geom.Point;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.Instant;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User implements Serializable, UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_gen")
    @SequenceGenerator(name = "users_id_gen", sequenceName = "users_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "phone_number", length = 15)
    private String phoneNumber;

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "middle_name", length = 100)
    private String middleName;

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Embedded
    private Address address;;

    @Column(name = "preferred_location", columnDefinition = "geometry(Point, 4326)")
    private Point preferredLocation;

    @OneToOne(mappedBy = "user")
    @JsonIgnore
    private Wishlist wishlist;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    @JsonIgnore
    private Instant createdAt;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    @JsonIgnore
    private Instant updatedAt;

    @ColumnDefault("'USER'")
    @Column(name = "role", nullable = false, length = 6)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @OneToMany(mappedBy = "mainOwner")
    @JsonIgnore
    private List<Store> ownedStores;

    @ColumnDefault("true")
    @Column(name = "is_active")
    private Boolean isActive;
    @Column(name = "last_login")
    private Instant lastLogin;

    @Override
    public boolean equals(Object o) {
        return o instanceof User && ((User) o).getId().equals(this.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.toString()));
    }

    @Override
    public String getPassword() {
        return passwordHash;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }
}