package com.example.banksystem.client;

import com.example.banksystem.credit.Credit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    @Query("SELECT s FROM Client s WHERE s.passport = ?1")
    Optional<Client> findClientByPassport(String passport);

    @Query("update Client u set u.credits = :credit where u.id = :id")
    default void updateCredits(@Param(value = "id") long id, @Param(value = "credit") Credit credits) {

    }

}
