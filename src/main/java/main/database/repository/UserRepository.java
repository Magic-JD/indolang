package main.database.repository;

import main.database.model.DbUserItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface UserRepository extends MongoRepository<DbUserItem, String> {

    @Query("{username:'?0'}")
    Optional<DbUserItem> findItemByName(String username);

    long count();
}
