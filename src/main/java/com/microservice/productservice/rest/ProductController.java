package com.microservice.productservice.rest;

import com.microservice.productservice.payload.ProductRequest;
import com.microservice.productservice.payload.response.ProductResponse;
import com.microservice.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
@Log4j2
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Long> addProduct(@RequestBody ProductRequest productRequest) {

        log.info("Adding new product: {}", productRequest);

        long productId = productService.addProduct(productRequest);
        //return new ResponseEntity<>(productId, HttpStatus.CREATED);
        return ResponseEntity.status(HttpStatus.CREATED).body(productId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable("id") long productId) {

        log.info("ProductController | getProductById is called");

        log.info("ProductController | getProductById | productId : {}", productId);

        ProductResponse productResponse
                = productService.getProductById(productId);
        //return new ResponseEntity<>(productResponse, HttpStatus.OK);
        return ResponseEntity.ok(productResponse);
    }

    @PutMapping("/reduceQuantity/{id}")
    public ResponseEntity<Void> reduceQuantity(
            @PathVariable("id") long productId,
            @RequestParam long quantity
    ) {

        log.info("ProductController | reduceQuantity is called");

        log.info("ProductController | reduceQuantity | productId : {}", productId);
        log.info("ProductController | reduceQuantity | quantity : {}", quantity);

        productService.reduceQuantity(productId,quantity);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProductById(@PathVariable("id") long productId) {
        productService.deleteProductById(productId);
    }
}
