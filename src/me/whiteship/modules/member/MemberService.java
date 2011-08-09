package me.whiteship.modules.member;

import java.util.List;

import me.whiteship.domain.Member;

public interface MemberService {
	
	public Member getAMember(Integer id);
	
	public List<Member> getList(String keyword);
	
	public void remove(String name);

}
