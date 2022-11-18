package com.example.banksystem.credit;

import com.example.banksystem.client.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CreditRepository extends JpaRepository<Credit, Long> {
    @Query("SELECT c FROM Credit c WHERE c.client.lastname LIKE ?1%")
    List<Credit> findCreditByClient(String lastname);
}
