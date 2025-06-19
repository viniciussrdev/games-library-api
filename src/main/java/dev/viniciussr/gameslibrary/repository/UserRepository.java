package dev.viniciussr.gameslibrary.repository;

import dev.viniciussr.gameslibrary.enums.Plans;
import dev.viniciussr.gameslibrary.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByName(String name);

    List<User> findByEmail(String email);

    List<User> findByPlan(Plans plan);
}
