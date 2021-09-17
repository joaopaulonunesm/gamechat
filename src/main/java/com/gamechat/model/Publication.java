package com.gamechat.model;

import java.time.LocalDateTime;
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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
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
	private LocalDateTime createDate;

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
}
