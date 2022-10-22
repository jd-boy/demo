package org.jd.demo.jpa.domain.user.repository;

import org.jd.demo.jpa.domain.BaseRepository;
import org.jd.demo.jpa.domain.user.User;
import org.springframework.stereotype.Repository;

/**
 * @Auther jd
 */
@Repository
public interface UserRepository extends BaseRepository<User, Long> {

}
