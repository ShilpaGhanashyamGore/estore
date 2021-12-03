package com.example.estore.category;

import com.example.estore.entity.Category;
import com.example.estore.exceptions.DuplicateResourceException;
import com.example.estore.exceptions.ResourceNotFoundException;
import com.example.estore.repository.CategoryRepository;
import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(@Autowired CategoryService catService){
        categoryService = catService;
    }

    @GetMapping(value="/category")
    public ResponseEntity<List<CategoryDTO>> getAllCategories(){
        List<CategoryDTO> listOfCategories = categoryService.findAllCategories();
             return ResponseEntity.status(HttpStatus.OK)
                     .contentType(MediaType.APPLICATION_JSON)
                     .body(listOfCategories);
    }

    @GetMapping(value="/category/{id}")
    public ResponseEntity<CategoryDTO> getCategoryByCategoryId(@RequestParam long categoryId){
        Optional<CategoryDTO> categoryDtO = categoryService.findCategoryByCategoryId(categoryId);
        if(categoryDtO.isPresent()) {
            return ResponseEntity.status(HttpStatus.FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(categoryDtO.get());
        }else {
            throw new ResourceNotFoundException(" Category with id "+categoryId+ " does not exist");
        }
    }

    @GetMapping(value="/category/{name}")
    public ResponseEntity<CategoryDTO> getCategoryByName(@NonNull @PathVariable String name){
        Optional<CategoryDTO> categoryDtO = categoryService.findCategoryByCategoryName(name);
        if(categoryDtO.isPresent()) {
            return ResponseEntity.status(HttpStatus.FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(categoryDtO.get());
        }else {
            throw new ResourceNotFoundException(" Category with name "+name+ " does not exist");
        }
    }

    @PostMapping(value="/category")
    public ResponseEntity<CategoryDTO> addCategory(@Valid @RequestBody CategoryDTO catToAdd){
        Optional<CategoryDTO> category = categoryService.addNewCategory(catToAdd);
        if(category.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(category.get());
        }else{
            throw new DuplicateResourceException(" Resource to be added already exists");
        }
    }

    @PutMapping(value="/category")
    public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryDTO catToUpdate){
        Optional<CategoryDTO> category = categoryService.updateExistingCategory(catToUpdate);
        if(category.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(category.get());
        }else{
            throw new ResourceNotFoundException("Category to be updated does not exist");
        }
    }

    @DeleteMapping(value="/category")
    public ResponseEntity<HttpStatus> deleteCategoryByCategoryId(@NonNull @RequestParam Long id){
        Optional<CategoryDTO> category = categoryService.deleteCategoryById(id);
        if(category.isPresent()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .contentType(MediaType.APPLICATION_JSON).build();
        }else{
            throw new ResourceNotFoundException("Resource with id "+id+ "was not found");
        }
    }

    @DeleteMapping(value="/category/{name}")
    public ResponseEntity<HttpStatus> deleteCategoryByName(@NotEmpty @PathVariable(value = "name") String name){
        Optional<CategoryDTO> category = categoryService.deleteCategoryByName(name);
        if(category.isPresent()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .contentType(MediaType.APPLICATION_JSON).build();
        }else{
            throw new ResourceNotFoundException("Resource with name "+name+ "was not found");
        }
    }
}
