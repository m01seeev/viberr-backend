package org.viberrbackend.Profile;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProfileRepository extends MongoRepository<ProfileModel, String> {
    ProfileModel findByUserId(String userId);
}
