package com.yinjun.loginback.repo;

import com.yinjun.loginback.entity.SysUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<SysUserEntity,Long> {
     List<SysUserEntity> findByLoginName(String loginName);
}
