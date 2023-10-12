package io.rainett.deliverybackend.repository;

import io.rainett.deliverybackend.model.UserBase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserBaseRepository extends JpaRepository<UserBase, Long> {

    Optional<UserBase> findByEmail(String email);

}
