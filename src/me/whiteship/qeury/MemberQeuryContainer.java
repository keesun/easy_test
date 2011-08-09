package me.whiteship.qeury;

import org.springframework.stereotype.Component;

import me.whiteship.domain.QMember;

import com.mysema.query.types.Predicate;

@Component
public class MemberQeuryContainer {
	
	public Predicate nameContains(String keyword) {
		QMember member = QMember.member;
		return member.name.contains(keyword);
	}
	
	public Predicate nameEqualsTo(String keyword) {
		QMember member = QMember.member;
		return member.name.eq(keyword);
	}

}
