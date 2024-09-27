package com.kosine.ImageServer.loginrepository;


import com.kosine.ImageServer.loginmodels.ERole;
import com.kosine.ImageServer.loginmodels.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role, String> {
  Optional<Role> findByName(ERole name);
}
