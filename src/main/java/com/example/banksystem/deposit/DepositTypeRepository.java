package com.example.banksystem.deposit;

import com.example.banksystem.credit.CreditType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepositTypeRepository extends JpaRepository<DepositType, Long> {
    List<DepositType> findByNameStartsWithIgnoreCase(String name);
}
