package com.alibou.security.repositories;

import com.alibou.security.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepo extends JpaRepository<Address,Integer> {

    Optional<Address> findById(Long id);

}
