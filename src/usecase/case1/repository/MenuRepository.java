package usecase.case1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import usecase.case1.domain.Menu;

public interface MenuRepository extends JpaRepository<Menu, Long> {

}
