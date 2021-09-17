package com.gamechat.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Game {

	@Id
	@SequenceGenerator(name = "GAM_SEQ", sequenceName = "GAME_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "GAM_SEQ")
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String genre;

	private String description;

	@Lob
	@Type(type = "org.hibernate.type.TextType")
	private String picture;
}
