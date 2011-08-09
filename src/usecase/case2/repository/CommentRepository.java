package usecase.case2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import usecase.case2.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
