package com.spring.cosmos.ebookstore.model.user;
import com.azure.spring.data.cosmos.repository.CosmosRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CosmosRepository<Customer, String> {
}
