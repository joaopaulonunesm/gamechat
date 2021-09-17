package com.gamechat.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "token")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticatedToken {

	@Id
	@SequenceGenerator(name = "AUTTOKEN_SEQ", sequenceName = "AUTTOKENSEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "AUTTOKEN_SEQ")
	private Long id;
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_login")
	private Login login;
	private String token;
	private LocalDateTime expirationDate;
}
