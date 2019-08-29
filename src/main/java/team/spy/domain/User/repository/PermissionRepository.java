package team.spy.domain.User.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import team.spy.domain.User.entity.MenuPermission;

public interface PermissionRepository extends JpaRepository<MenuPermission, Long>{

}
