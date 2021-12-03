package com.example.estore.product;

import com.example.estore.exceptions.DuplicateResourceException;
import com.example.estore.exceptions.ResourceNotFoundException;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Optional;

@RestController
public final class ProductController {

    private final ProductService productService;

    public ProductController(@Autowired ProductService pService){
        productService = pService;
    }

    @GetMapping(value="/product")
    public ResponseEntity<List<ProductDTO>> getAllProducts(){
        List<ProductDTO> listOfProducts = productService.findAllProducts();
        return ResponseEntity.status(HttpStatus.OK)
                   .contentType(MediaType.APPLICATION_JSON)
                   .body(listOfProducts);
    }

    @GetMapping(value="/product/{id}")
    public ResponseEntity<ProductDTO> getProductById(@NonNull @PathVariable long productId){
        Optional<ProductDTO> product = productService.findProductById(productId);
        if(product.isPresent()){
            return ResponseEntity.status(HttpStatus.FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(product.get());
        }else{
            throw new ResourceNotFoundException("Resource with id "+productId+ " does not exist");
        }

    }

    @GetMapping(value="/product/{name}")
    public ResponseEntity<ProductDTO> getProductByName(@NonNull @PathVariable String name){
        Optional<ProductDTO> product = productService.findProductByName(name);
        if(product.isPresent()){
            return ResponseEntity.status(HttpStatus.FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(product.get());
        }else{
            throw new ResourceNotFoundException("Resource with name "+name+ " does not exist");
        }
    }

    @PostMapping(value="/product")
    public ResponseEntity<ProductDTO> addProduct(@Valid @RequestBody ProductDTO productToAdd){
        Optional<ProductDTO> product = productService.addNewProduct(productToAdd);
        if(product.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(product.get());
        }else{
            throw new DuplicateResourceException("Resource with name "+productToAdd.getProductName()+ "was not created");
        }
    }

    @PutMapping(value="/product")
    public ResponseEntity<ProductDTO> updateProduct(@Valid @RequestBody ProductDTO productToUpdate){
        Optional<ProductDTO> product = productService.updateExistingProduct(productToUpdate);
        if(product.isPresent()){
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(product.get());
        }else{
            throw new ResourceNotFoundException("Resource with name "+productToUpdate.getProductName()+ "was not found");
        }
    }

    @DeleteMapping(value="/product/{id}")
    public ResponseEntity<HttpStatus> deleteProductById(@NonNull @PathVariable long productId){
        Optional<ProductDTO> product = productService.deleteProductById(productId);
        if(product.isPresent()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .contentType(MediaType.APPLICATION_JSON).build();
        }else{
            throw new ResourceNotFoundException("Resource with id "+productId+ "was not found");
        }
    }


    @DeleteMapping(value="/product")
    public ResponseEntity<HttpStatus> deleteProductByName(@NotEmpty @RequestParam(value = "name") String productName){
        Optional<ProductDTO> product = productService.deleteProductByName(productName);
        if(product.isPresent()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .contentType(MediaType.APPLICATION_JSON).build();
        }else{
            throw new ResourceNotFoundException("Resource with name "+productName+ "was not found");
        }
    }

}
