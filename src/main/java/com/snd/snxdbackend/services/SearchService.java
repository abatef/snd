package com.snd.snxdbackend.services;

import com.snd.snxdbackend.dtos.ProductSearch;
import com.snd.snxdbackend.dtos.store.ProductDTO;
import com.snd.snxdbackend.models.Product;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {
    private ElasticsearchOperations elasticsearchOperations;
    public SearchService(ElasticsearchOperations elasticsearchOperations) {
        this.elasticsearchOperations = elasticsearchOperations;
    }

    public List<ProductSearch> searchProductName(String name, int page, int size) {
        Criteria criteria = new Criteria("description").fuzzy(name);
        CriteriaQuery criteriaQuery = new CriteriaQuery(criteria, PageRequest.of(page, size));
        SearchHits<ProductSearch> searchHits = elasticsearchOperations.search(criteriaQuery, ProductSearch.class);
        return searchHits.getSearchHits().stream().map(SearchHit::getContent).toList();
    }


    public boolean addProduct(Product dto) {
        ProductSearch search = new ProductSearch();
        search.setDescription(dto.getDescription());
        search.setName(dto.getName());
        search.setCategory(dto.getCategory());
        search.setId(dto.getId());
        search = elasticsearchOperations.save(search);
        return true;
    }
}
