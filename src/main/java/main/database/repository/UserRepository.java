package main.database.repository;

import main.database.model.DbUserDetails;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface UserRepository extends MongoRepository<DbUserDetails, String> {

    @Query("{username:'?0'}")
    DbUserDetails findItemByName(String username);

    long count();
}
