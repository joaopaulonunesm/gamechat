package com.gamechat.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "user_acount")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @SequenceGenerator(name = "USER_SEQ", sequenceName = "USER_SEQUENCE", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "USER_SEQ")
    private Long id;

    @Column(name = "nick_name", nullable = false)
    private String nickName;

    private String name;

    @Column(name = "last_name")
    private String lastName;

    private String country;

    private String city;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_game_main")
    private Game mainGame;

    private String description;

    private String facebook;

    private String twitter;

    private String youtube;

    private String twitch;

    @Column(name = "birth_date")
    private String birthDate;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "profile_picture")
    private String profilePicture;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "cover_picture")
    private String coverPicture;

    @Column(name = "amount_followings")
    private int amountFollowings;

    @Column(name = "amount_followers")
    private int amountFollowers;

    @Column(name = "amount_publications")
    private int amountPublications;
}
