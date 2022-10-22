package org.jd.demo.jpa.domain.usergroup.repository;

import org.jd.demo.jpa.domain.BaseRepository;
import org.jd.demo.jpa.domain.usergroup.UserGroup;
import org.springframework.stereotype.Repository;

/**
 * @Auther jd
 */
@Repository
public interface UserGroupRepository extends BaseRepository<UserGroup, Long> {

}
