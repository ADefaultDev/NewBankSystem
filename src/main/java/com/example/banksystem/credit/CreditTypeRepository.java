package com.example.banksystem.credit;

import com.example.banksystem.client.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CreditTypeRepository extends JpaRepository<CreditType, Long> {
    List<CreditType> findByNameStartsWithIgnoreCase(String name);

}
