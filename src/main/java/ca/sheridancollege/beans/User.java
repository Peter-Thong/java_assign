package ca.sheridancollege.beans;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
	private long userId;
	private String userName;
	private String encryptedPassword;
}
