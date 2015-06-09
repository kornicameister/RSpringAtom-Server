package org.agatom.springatom.data.repo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * <p>
 * <small>Class is a part of <b>SpringAtom2</b> and was created at 2014-09-25</small>
 * </p>
 *
 * @author trebskit
 * @version 0.0.1
 * @since 0.0.1
 */
@NoRepositoryBean
public interface NRepository<T>
  extends JpaRepository<T, Long>,
  QueryDslPredicateExecutor<T>,
  CrudRepository<T, Long> {

}
