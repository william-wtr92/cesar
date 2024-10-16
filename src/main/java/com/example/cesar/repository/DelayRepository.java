package com.example.cesar.repository;

import com.example.cesar.entity.Delay;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DelayRepository extends JpaRepository<Delay, Long> {
}
