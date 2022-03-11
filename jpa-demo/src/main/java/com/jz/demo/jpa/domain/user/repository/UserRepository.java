package com.jz.demo.jpa.domain.user.repository;

import com.jz.demo.jpa.domain.BaseRepository;
import com.jz.demo.jpa.domain.user.User;
import org.springframework.stereotype.Repository;

/**
 * @Auther jd
 */
@Repository
public interface UserRepository extends BaseRepository<User, Long> {

}
