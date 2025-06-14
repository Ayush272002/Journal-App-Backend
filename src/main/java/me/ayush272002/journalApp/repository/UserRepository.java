package me.ayush272002.journalApp.repository;

import me.ayush272002.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId> {
    User findByUserName (String username);
    void deleteByUserName (String username);
}
