package com.alibou.security.repositories;

import com.alibou.security.models.DeliveryMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeliveryMethodRepo extends JpaRepository<DeliveryMethod,Integer> {

   Optional<DeliveryMethod> findByDeliveryId(Long id);

}
