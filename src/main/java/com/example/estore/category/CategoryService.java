package com.example.estore.category;

import com.example.estore.entity.Category;
import com.example.estore.exceptions.DependenciesFoundException;
import com.example.estore.exceptions.UnsupportedCategoryException;
import com.example.estore.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public CategoryService(@Autowired CategoryRepository catRepo,
                           @Autowired ModelMapper mapper){
        categoryRepository = catRepo;
        modelMapper = mapper;
    }
    public List<CategoryDTO> findAllCategories(){
        return StreamSupport.stream(categoryRepository.findAll().spliterator(),false).map(this::toDTO).collect(Collectors.toList());
    }

    public Optional<CategoryDTO> findCategoryByCategoryId(long categoryId){
        return categoryRepository.findByCategoryId(categoryId)
                .map(category->modelMapper.map(category, CategoryDTO.class));
    }

    public Optional<CategoryDTO> findCategoryByCategoryName(String categoryName){
        return categoryRepository.findByName(categoryName)
                .map(category->modelMapper.map(category, CategoryDTO.class));
    }

    public Optional<CategoryDTO> addNewCategory(CategoryDTO categorydto){
        Category catFromDTO = modelMapper.map(categorydto, Category.class);
        Optional<Category> catFromDBByName = categoryRepository.findByName(catFromDTO.getName());
        Optional<Category> catFromDBById = categoryRepository.findById(catFromDTO.getId());
        if(catFromDBByName.isEmpty() && catFromDBById.isEmpty()) {
            if(categorydto.getParentCategory().equals("MediaMarkt_DE")) {
                catFromDTO.setParentId(Long.MAX_VALUE);
            }else{
                Optional<Category> parentCat = categoryRepository.findByName(categorydto.getParentCategory());
                parentCat.ifPresent(category -> catFromDTO.setParentId(parentCat.get().getCategoryId()));
            }
            return Optional.of(categoryRepository.save(catFromDTO))
                    .map(category -> modelMapper.map(category, CategoryDTO.class));
        }else {
            return Optional.empty();
        }

    }

    @Transactional
    public Optional<CategoryDTO> updateExistingCategory(CategoryDTO categoryDTO){
        Category catFromDTO = modelMapper.map(categoryDTO, Category.class);
        Optional<Category> catFromDBByName = categoryRepository.findByName(catFromDTO.getName());
        Optional<Category> catFromDBById = categoryRepository.findByCategoryId(catFromDTO.getCategoryId());
        Optional<Category> catFromDB = catFromDBByName
                .map(category -> categoryRepository.findById(category.getId()))
                .orElseGet(() -> categoryRepository.findById(catFromDBById.get().getId()));

        if(catFromDB.isPresent()) {
            Category toSave = catFromDB.get();
            toSave.setCategoryId(catFromDTO.getCategoryId());
            toSave.setName(catFromDTO.getName());
            Optional<Category> parentCategory = categoryRepository.findByName(categoryDTO.getParentCategory());
            if(parentCategory.isEmpty() && !categoryDTO.getParentCategory().equals("9223372036854775807")){
                throw new UnsupportedCategoryException("Parent category name is not present in DB");
            }else {
                parentCategory.ifPresent(category -> toSave.setParentId(category.getCategoryId()));
            }
            categoryRepository.save(toSave);

            List<Category> dependentCategories = StreamSupport.stream(categoryRepository.findAll().spliterator(),false)
                    .collect(Collectors.toList());
            dependentCategories.forEach(d->d.setParentId(toSave.getCategoryId()));
            categoryRepository.saveAll(dependentCategories);
            return Optional.of(modelMapper.map(catFromDTO,CategoryDTO.class));

        }else{
            return Optional.empty();
        }
    }

    public Optional<CategoryDTO> deleteCategoryByName(String name){
        Optional<Category> catFromDBByName = categoryRepository.findByName(name);
        if(catFromDBByName.isPresent()) {
            if(categoryRepository.findAllByParentId(catFromDBByName.get().getCategoryId()).isEmpty()){
                categoryRepository.delete(catFromDBByName.get());
            }else{
                throw new DependenciesFoundException("Child categories were found. Delete or Update them before this operation");
            }
            return catFromDBByName.map(category -> modelMapper.map(category, CategoryDTO.class));
        }else{
            return Optional.empty();
        }
    }

    public Optional<CategoryDTO> deleteCategoryById(Long id){
        Optional<Category> catFromDBById = categoryRepository.findByCategoryId(id);
        if(catFromDBById.isPresent()) {
            if(categoryRepository.findAllByParentId(catFromDBById.get().getCategoryId()).isEmpty()){
                categoryRepository.delete(catFromDBById.get());
            }else{
                throw new DependenciesFoundException("Child categories were found. Delete or Update them before this operation");
            }
            return catFromDBById.map(category -> modelMapper.map(category, CategoryDTO.class));
        }else{
            return Optional.empty();
        }
    }

    private CategoryDTO toDTO(Category cat){
        return modelMapper.map(cat, CategoryDTO.class);
    }

}