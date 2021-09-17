package com.gamechat.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comment_publication")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

	@Id
	@SequenceGenerator(name = "COMMENT_SEQ", sequenceName = "COMMENTSEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "COMMENT_SEQ")
	private Long id;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_publication")
	private Publication publication;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_user_commentator")
	private User user;
	private String text;
	private LocalDateTime moment;
}
