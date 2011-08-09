package usecase.case2;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import usecase.case2.domain.Post;
import usecase.case2.domain.User;
import usecase.case2.repository.CommentRepository;
import usecase.case2.repository.PostRepository;
import usecase.case2.repository.UserRepository;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
@Transactional
public class Case2Test {
	
	@Autowired PostRepository postRepository;
	@Autowired CommentRepository commentRepository;
	@Autowired UserRepository userRepository;
	
	@Test
	public void di(){
		User whiteship = new User();
		whiteship.setEmail("keesun@mail.com");
		whiteship.setName("keesun");
		
		userRepository.save(whiteship);
		
		Post post = new Post();
		post.setAuthor(whiteship);
		post.setTitle("Hibernate is good");
		post.setContent("First of all, Hibernate is ORM ...");
		
		postRepository.save(post);
		
		List<Post> posts = postRepository.findAll();
		
		assertThat(posts.size(), is(1));
		
		for(Post thisPost : posts) {
			User user = thisPost.getAuthor();
		}
		
	}
	

}
