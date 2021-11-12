package ECSE428Project.dao;

import ECSE428Project.model.AdminConfig;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminConfigRepository extends CrudRepository<AdminConfig, String> {

    AdminConfig findAdminConfigByName(String name);
}
