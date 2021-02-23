package com.spring.cosmos.ebookstore.model.book;

import com.azure.spring.data.cosmos.repository.CosmosRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends CosmosRepository<Book,String> {

}
