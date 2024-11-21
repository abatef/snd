package com.snd.snxdbackend.controllers;

import com.snd.snxdbackend.dtos.ProductSearch;
import com.snd.snxdbackend.services.SearchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/search")
public class SearchController {
    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/")
    public ResponseEntity<List<ProductSearch>> search(@RequestParam("name") String name,
                                                      @RequestParam(required = false, defaultValue = "0") String page,
                                                      @RequestParam(required = false, defaultValue = "10") String size) {
        List<ProductSearch> list = searchService.searchProductName(name, Integer.parseInt(page), Integer.parseInt(size));
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

}
