package com.jz.demo.jpa.domain.user.repository;

import com.jz.demo.jpa.domain.BaseRepository;
import com.jz.demo.jpa.domain.user.UserGroup;
import org.springframework.stereotype.Repository;

/**
 * @Auther jd
 */
@Repository
public interface UserGroupRepository extends BaseRepository<UserGroup, Long> {

}
