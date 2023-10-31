package br.com.leonardo.authserviceapi.repository;

import br.com.leonardo.authserviceapi.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String > {

    Optional<User> findByEmail(final String email);
}
