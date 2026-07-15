package ru.kafis.praktik.repository;

import ru.kafis.praktik.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}