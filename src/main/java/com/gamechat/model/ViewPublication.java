package com.gamechat.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "view_publication")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ViewPublication {

	@Id
	@SequenceGenerator(name = "VIEWPUB_SEQ", sequenceName = "VIEWPUBSEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "VIEWPUB_SEQ")
	private Long id;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_publication")
	private Publication publication;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_user_view")
	private User user;
	private LocalDateTime moment;
}
