package usecase.case1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import usecase.case1.domain.Function;

public interface FunctionRepository extends JpaRepository<Function, Long> {

}
