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

import org.hibernate.annotations.Type;

@Entity
@Table(name = "user_acount")
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Game getMainGame() {
		return mainGame;
	}

	public void setMainGame(Game mainGame) {
		this.mainGame = mainGame;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFacebook() {
		return facebook;
	}

	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}

	public String getTwitter() {
		return twitter;
	}

	public void setTwitter(String twitter) {
		this.twitter = twitter;
	}

	public String getYoutube() {
		return youtube;
	}

	public void setYoutube(String youtube) {
		this.youtube = youtube;
	}

	public String getTwitch() {
		return twitch;
	}

	public void setTwitch(String twitch) {
		this.twitch = twitch;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}

	public String getCoverPicture() {
		return coverPicture;
	}

	public void setCoverPicture(String coverPicture) {
		this.coverPicture = coverPicture;
	}

	public int getAmountFollowings() {
		return amountFollowings;
	}

	public void setAmountFollowings(int amountFollowings) {
		this.amountFollowings = amountFollowings;
	}

	public int getAmountFollowers() {
		return amountFollowers;
	}

	public void setAmountFollowers(int amountFollowers) {
		this.amountFollowers = amountFollowers;
	}

	public int getAmountPublications() {
		return amountPublications;
	}

	public void setAmountPublications(int amountPublications) {
		this.amountPublications = amountPublications;
	}

}
