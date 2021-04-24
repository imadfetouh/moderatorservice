package com.imadelfetouh.moderatorservice.dal.ormmodel;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user")
public class User implements Serializable {

    public User() {

    }

    public User(String userId, String username, Role role, String photo, Profile profile) {
        this.userId = userId;
        this.username = username;
        this.role = role;
        this.photo = photo;
        this.profile = profile;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "userId")
    private String userId;

    @Column(name = "username")
    private String username;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Column(name = "photo")
    private String photo;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_Id", referencedColumnName = "profileId")
    private Profile profile;

    public String getUsername() {
        return username;
    }

    public String getPhoto() {
        return photo;
    }

    public Profile getProfile() {
        return profile;
    }
}
