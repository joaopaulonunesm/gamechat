package com.gamechat.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Follow {

	@Id
	@SequenceGenerator(name = "FOLLOW_SEQ", sequenceName = "FOLLOW_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "FOLLOW_SEQ")
	private Long id;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_user", nullable = false)
	private User user;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_user_following", nullable = false)
	private User following;
	private LocalDateTime moment;
}
