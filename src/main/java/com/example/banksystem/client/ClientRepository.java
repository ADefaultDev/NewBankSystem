package com.example.banksystem.client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    @Query("SELECT s FROM Client s WHERE s.passport = ?1")
    Optional<Client> findClientByPassport(double passport);

}
