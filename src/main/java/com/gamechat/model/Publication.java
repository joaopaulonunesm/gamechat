package com.gamechat.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

@Entity
public class Publication {

	@Id
	@SequenceGenerator(name = "PUB_SEQ", sequenceName = "PUBLICATION_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "PUB_SEQ")
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_user", nullable = false)
	private User user;

	@OneToOne(optional = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "id_game")
	private Game game;

	@Lob
	@Type(type = "org.hibernate.type.TextType")
	@Column(nullable = false)
	private String text;

	@Column(name = "create_date", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;

	@Lob
	@Type(type = "org.hibernate.type.TextType")
	private String picture;

	@Lob
	@Type(type = "org.hibernate.type.TextType")
	private String video;

	@Column(name = "amount_likes")
	private int amountLikes;

	@Column(name = "amount_shares")
	private int amountShares;

	@Column(name = "amount_views")
	private int amountViews;

	@Column(name = "amount_comments")
	private int amountComments;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	public int getAmountLikes() {
		return amountLikes;
	}

	public void setAmountLikes(int amountLikes) {
		this.amountLikes = amountLikes;
	}

	public int getAmountShares() {
		return amountShares;
	}

	public void setAmountShares(int amountShares) {
		this.amountShares = amountShares;
	}

	public int getAmountViews() {
		return amountViews;
	}

	public void setAmountViews(int amountViews) {
		this.amountViews = amountViews;
	}

	public int getAmountComments() {
		return amountComments;
	}

	public void setAmountComments(int amountComments) {
		this.amountComments = amountComments;
	}

}
