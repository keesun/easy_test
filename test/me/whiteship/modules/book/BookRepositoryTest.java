package me.whiteship.modules.book;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import me.whiteship.domain.Book;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class BookRepositoryTest {
	
	@Autowired BookRepository repository;
	
	@Test
	public void di(){
		assertThat(repository, is(notNullValue()));
	}

	@Test
	public void crud(){
		Book book = new Book();
		book.setAuthor("keesun");
		book.setTitle("MISH");
		book.setPrice(50000);
		book.setEdition(1);
		
		repository.save(book);
		
		List<Book> bookList = repository.findAll();
		assertThat(bookList.size(), is(1));
	}
	
}
