package com.jz.demo.jpa.domain.permission.repository;

import com.jz.demo.jpa.domain.BaseRepository;
import com.jz.demo.jpa.domain.permission.Permission;
import org.springframework.stereotype.Repository;

/**
 * @Auther jd
 */
@Repository
public interface PermissionRepository extends BaseRepository<Permission, Long> {

}