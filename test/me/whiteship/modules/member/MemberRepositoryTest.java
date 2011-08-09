package me.whiteship.modules.member;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import me.whiteship.domain.Member;
import me.whiteship.domain.QMember;
import me.whiteship.qeury.MemberQeuryContainer;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mysema.query.jpa.impl.JPAQuery;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class MemberRepositoryTest {
	
	@Autowired MemberRepository repository;
	@Autowired MemberQeuryContainer mqc;
	@Autowired EntityManagerFactory entityManagerFactory;
	
	@Before
	public void setUp(){
		repository.deleteAll();
	}
	
	@Test
	public void jpaQuery(){
		//GIVEN
		Member m1 = new Member();
		m1.setName("bks");
		repository.save(m1);
	
		//WHEN
		QMember qMember = QMember.member; 
		JPAQuery query = new JPAQuery(entityManagerFactory.createEntityManager());
		
		//THEN
		Member member = query.from(qMember).where(qMember.name.eq("bks")).uniqueResult(qMember);
		assertThat(member, is(notNullValue()));
	}
	
	@Test
	public void queryDSL(){
		//GIVEN
		Member m1 = new Member();
		m1.setName("bks");
		repository.save(m1);
		String keyword = "ks";
		
		//WHEN
		long countOfContainsKS = repository.count(mqc.nameContains(keyword));
		//THEN
		assertThat(countOfContainsKS, is(1l));
		
		//WHEN
		long countOfEqKS = repository.count(mqc.nameEqualsTo(keyword));
		//THEN
		assertThat(countOfEqKS, is(0l));
	}
	
	@Test
	public void queryMethod(){
		//GIVEN
		Member m1 = new Member();
		m1.setName("bks");
		repository.save(m1);
		String keyword = "ks";
		
		//WHEN
		List<Member> memberList = repository.findByNameLike("%"+keyword+"%");
		//THEN
		assertThat(memberList.size(), is(1));
		
		//WHEN
		memberList = repository.findByName(keyword);
		//THEN
		assertThat(memberList.size(), is(0));
	}

}
