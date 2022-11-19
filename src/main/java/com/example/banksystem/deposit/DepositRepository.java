package com.example.banksystem.deposit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DepositRepository extends JpaRepository<Deposit,Long> {
    @Query("SELECT d FROM Deposit d WHERE d.client.lastname LIKE ?1%")
    List<Deposit> findDepositByClient(String lastname);
}
