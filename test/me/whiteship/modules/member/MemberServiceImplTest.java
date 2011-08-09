package me.whiteship.modules.member;


import java.util.List;

import me.whiteship.domain.Member;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class MemberServiceImplTest {
	
	@Autowired MemberService service;
	
	@Test
	public void getMemberCache(){
		service.getAMember(1);
		service.getAMember(1);
		service.getAMember(1);
		service.getAMember(1);
		
		service.getAMember(2);
		service.getAMember(2);
		service.getAMember(2);
		
		service.getAMember(3);
		service.getAMember(3);
	}
	
	@Test
	public void getListCache(){
		service.getList("ks");
		service.getList("ks");
		
		service.getList("uz");
		service.getList("uz");
	}
	
	@Test
	public void getListCacheWithDelete(){
		service.getList("ks");
		service.getList("ks");
		service.remove("ks");
		List<Member> memberList = service.getList("ks");
		for(Member member : memberList) {
			System.out.println("======================");
			System.out.println("after delete: " + member.getName());
		}
		
		service.getList("uz");
		service.getList("uz");
	}
	
}
