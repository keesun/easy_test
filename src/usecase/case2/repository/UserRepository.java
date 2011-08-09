package usecase.case2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import usecase.case2.domain.User;

public interface UserRepository extends JpaRepository<User, Long>  {

}
