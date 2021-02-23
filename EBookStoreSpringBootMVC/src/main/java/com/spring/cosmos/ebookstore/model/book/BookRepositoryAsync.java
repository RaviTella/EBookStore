package com.spring.cosmos.ebookstore.model.book;

import com.azure.cosmos.CosmosAsyncClient;
import com.azure.cosmos.CosmosAsyncContainer;
import com.azure.cosmos.models.CosmosQueryRequestOptions;
import com.azure.cosmos.util.CosmosPagedFlux;
import com.azure.spring.data.cosmos.core.query.CosmosPageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookRepositoryAsync {
    private CosmosAsyncClient cosmosAsyncClient;
    private final BookRepository bookRepository;

    @Autowired
    public BookRepositoryAsync(CosmosAsyncClient cosmosAsyncClient, BookRepository bookRepository) {
        this.cosmosAsyncClient = cosmosAsyncClient;
        this.bookRepository = bookRepository;
    }

    private CosmosAsyncContainer getAsyncContainer() {
        return cosmosAsyncClient
                .getDatabase("store")
                .getContainer("book");
    }

    public Response getBooks1(int preferredPageSize, int pagesToReturn) {
        CosmosQueryRequestOptions queryOptions = new CosmosQueryRequestOptions();
        String query = "SELECT * FROM Book";
        CosmosPagedFlux<Book> pagedFluxResponse =
                getAsyncContainer()
                        .queryItems(
                                query, queryOptions, Book.class);
        return pagedFluxResponse
                .byPage(preferredPageSize)
                .take(pagesToReturn)
                .map(page -> {
                    return new Response(page.getContinuationToken(), page.getResults());
                })
                .blockFirst();
    }


    public Response getBooks(int preferredPageSize, int pagesToReturn) {
        CosmosQueryRequestOptions queryOptions = new CosmosQueryRequestOptions();
        String query = "SELECT * FROM Book";
        CosmosPagedFlux<Book> pagedFluxResponse =
                getAsyncContainer()
                        .queryItems(
                                query, queryOptions, Book.class);
        return pagedFluxResponse
                .byPage(preferredPageSize)
                .take(pagesToReturn)
                .map(page -> {
                    return new Response(page.getContinuationToken(), page.getResults());
                })
                .blockFirst();
    }


    public Response getBooks(String continuationToken, int preferredPageSize, int pagesToReturn) {
        CosmosQueryRequestOptions queryOptions = new CosmosQueryRequestOptions();
        String query = "SELECT * FROM Book";
        CosmosPagedFlux<Book> pagedFluxResponse = getAsyncContainer()
                .queryItems(
                        query, queryOptions, Book.class);
        return pagedFluxResponse
                .byPage(continuationToken, preferredPageSize)
                .take(pagesToReturn)
                .map(page -> {
                    return new Response(page.getContinuationToken(), page.getResults());
                })
                .blockFirst();
    }


}
