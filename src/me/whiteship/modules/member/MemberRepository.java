package me.whiteship.modules.member;

import java.util.List;

import me.whiteship.domain.Member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

public interface MemberRepository extends JpaRepository<Member, Integer>, QueryDslPredicateExecutor<Member>  {
	
	List<Member> findByNameLike(String keyword);

	List<Member> findByName(String keyword);
}
