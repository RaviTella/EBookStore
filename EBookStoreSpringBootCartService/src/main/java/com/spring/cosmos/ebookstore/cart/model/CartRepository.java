package com.spring.cosmos.ebookstore.cart.model;

import com.azure.spring.data.cosmos.repository.CosmosRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends CosmosRepository<Cart, String> {

}
