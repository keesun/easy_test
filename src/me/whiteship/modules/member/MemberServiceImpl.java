package me.whiteship.modules.member;

import java.util.ArrayList;
import java.util.List;

import me.whiteship.domain.Member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.ehcache.annotations.Cacheable;
import com.googlecode.ehcache.annotations.TriggersRemove;

@Service
@Transactional
public class MemberServiceImpl implements MemberService {

	List<Member> members;

	public MemberServiceImpl() {
		members = new ArrayList<Member>();
		
		Member keesun = new Member();
		keesun.setName("ks");
		members.add(keesun);
		Member uzin = new Member();
		uzin.setName("uz");
		members.add(uzin);
	}

	@Autowired
	MemberRepository repository;

	@Cacheable(cacheName = "member")
	public Member getAMember(Integer id) {
		System.out.println("======================");
		System.out.println("id: " + id);
		return repository.findOne(id);
	}

	boolean flag = true;
	
	@Cacheable(cacheName = "memberList")
	public List<Member> getList(String keyword) {
		System.out.println("======================");
		System.out.println("getList: " + keyword);
		return members;
	}

	@TriggersRemove(cacheName = "memberList")
	public void remove(String name) {
		System.out.println("======================");
		System.out.println("remove: " + name);
		int index = 0;
		for(Member member : members) {
			if(member.getName().equals(name)){
				index = members.indexOf(member);
			}
		}
		members.remove(index);
	}

}
