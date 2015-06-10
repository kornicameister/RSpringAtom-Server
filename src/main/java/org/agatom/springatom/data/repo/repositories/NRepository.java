package org.agatom.springatom.data.repo.repositories;

import com.mysema.query.types.Predicate;
import org.agatom.springatom.security.SecurityRoles;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.security.access.annotation.Secured;

import java.util.List;

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

  @Override
  @Secured({SecurityRoles.ROLE_ADMIN, SecurityRoles.ROLE_USER})
  List<T> findAll();

  @Override
  @Secured({SecurityRoles.ROLE_ADMIN, SecurityRoles.ROLE_USER})
  List<T> findAll(Sort sort);

  @Override
  @Secured({SecurityRoles.ROLE_ADMIN, SecurityRoles.ROLE_USER})
  List<T> findAll(Iterable<Long> longs);

  @Override
  @Secured({SecurityRoles.ROLE_ADMIN, SecurityRoles.ROLE_USER})
  Page<T> findAll(Pageable pageable);

  @Override
  @Secured({SecurityRoles.ROLE_ADMIN, SecurityRoles.ROLE_USER})
  Page<T> findAll(Predicate predicate, Pageable pageable);

}
