package org.jd.demo.jpa.domain.permission.repository;

import org.jd.demo.jpa.domain.BaseRepository;
import org.jd.demo.jpa.domain.permission.Permission;
import org.springframework.stereotype.Repository;

/**
 * @Auther jd
 */
@Repository
public interface PermissionRepository extends BaseRepository<Permission, Long> {

}