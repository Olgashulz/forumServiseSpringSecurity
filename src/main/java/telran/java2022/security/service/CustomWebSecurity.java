package telran.java2022.security.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import telran.java2022.accounting.dao.UserAccountRepository;
import telran.java2022.post.dao.PostRepository;
import telran.java2022.post.model.Post;

@Service("customSecurity")@RequiredArgsConstructor
public class CustomWebSecurity {
	//private static final int EXPIRATION_DATE = 60;
	
	final PostRepository postRepository;
	final UserAccountRepository userRepository;
	
	public boolean checkPostAuthor(String postId, String userName) {
		Post post = postRepository.findById(postId).orElse(null);
		return post != null && userName.equalsIgnoreCase(post.getAuthor());
	}
	
//	public boolean checkDataCreationsPassword(String login) {
//		 UserAccount user = userRepository.findById(login).orElse(null);
//		 LocalDateTime dateCreation = user.getPasswordCreation();
//		 System.out.println(dateCreation);		 
//		 System.out.println(dateCreation.plusDays(EXPIRATION_DATE).isAfter(LocalDateTime.now()));
//		return dateCreation.plusDays(EXPIRATION_DATE).isAfter(LocalDateTime.now());
//		
//	}

}
