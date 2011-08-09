package usecase.case2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import usecase.case2.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

}
