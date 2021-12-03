package com.example.estore.product;

import com.example.estore.entity.Category;
import com.example.estore.entity.LongDescription;
import com.example.estore.entity.Product;
import com.example.estore.entity.ShortDescription;
import com.example.estore.exceptions.UnsupportedCategoryException;
import com.example.estore.repository.CategoryRepository;
import com.example.estore.repository.LongDescriptionRepository;
import com.example.estore.repository.ProductRepository;
import com.example.estore.repository.ShortDescriptionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ShortDescriptionRepository shortDescRepository;
    private final LongDescriptionRepository longDescRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public ProductService(@Autowired ProductRepository productRepo,
                          @Autowired ShortDescriptionRepository shortDescRepo,
                          @Autowired LongDescriptionRepository longDescRepo,
                          @Autowired CategoryRepository catRepository,
                          @Autowired ModelMapper mMapper){
        productRepository = productRepo;
        shortDescRepository = shortDescRepo;
        longDescRepository = longDescRepo;
        categoryRepository = catRepository;
        modelMapper = mMapper;
    }

    public List<ProductDTO> findAllProducts(){
        return StreamSupport.stream(productRepository.findAll().spliterator(),false)
                .map(this::toDTO).collect(Collectors.toList());
    }

    public Optional<ProductDTO> findProductById(Long id){
        Optional<Product> prod = productRepository.findByProductId(id);
        return prod.map(product -> modelMapper.map(product, ProductDTO.class));
    }

    public Optional<ProductDTO> findProductByName(String name){
        Optional<Product> prod = productRepository.findByName(name);
        return prod.map(product -> modelMapper.map(product, ProductDTO.class));
    }

    @Transactional
    public Optional<ProductDTO> addNewProduct(ProductDTO productDTO){
        Product prodFromDTO = modelMapper.map(productDTO, Product.class);
        Optional<Product> prodFromDBByName = prodFromDTO.getName()!= null?
                productRepository.findByName(prodFromDTO.getName()): Optional.empty();
        Optional<Product> prodFromDBById = productDTO.getProductId()!= null?
                productRepository.findByProductId(productDTO.getProductId()): Optional.empty();
        if(prodFromDBByName.isEmpty() && prodFromDBById.isEmpty()) {
            prepareProductForAddOrUpdate(prodFromDTO);
            return Optional.of(productRepository.save(prodFromDTO)).map(product -> modelMapper.map(product, ProductDTO.class));
        }else {
            return Optional.empty();
        }

    }

    private void prepareProductForAddOrUpdate(Product prodFromDTO){
        Map<String,Long> shortDescMap = new HashMap<>();
        StreamSupport.stream(shortDescRepository.findAll().spliterator(), false)
                .forEach(entry->shortDescMap.put(entry.getDescription(), entry.getId()));
        if(shortDescMap.containsKey(prodFromDTO.getShortDescription())){
            prodFromDTO.setShortDescription(shortDescRepository
                    .findById(shortDescMap.get(prodFromDTO.getShortDescription())).get());
        }else{
            prodFromDTO.setShortDescription(new ShortDescription(prodFromDTO.getShortDescription().getDescription()));
        }

        Map<String,Long> longDescMap = new HashMap<>();
        StreamSupport.stream(longDescRepository.findAll().spliterator(), false)
                .forEach(entry->longDescMap.put(entry.getDescription(), entry.getId()));

        if(longDescMap.containsKey(prodFromDTO.getLongDescription().getDescription())){
            prodFromDTO.setLongDescription(longDescRepository
                    .findById(longDescMap.get(prodFromDTO.getLongDescription().getDescription())).get());
        }else{
            prodFromDTO.setLongDescription(new LongDescription(prodFromDTO.getLongDescription().getDescription()));
        }

        Optional<Category> productCat = categoryRepository.findByName(prodFromDTO.getCategory().getName());
        if(productCat.isEmpty()) {
            throw new UnsupportedCategoryException("No matching category exists");
        }else{
            prodFromDTO.setCategory(productCat.get());
        }
    }

    public Optional<ProductDTO> updateExistingProduct(ProductDTO productDTO){
        Product prodFromDTO = modelMapper.map(productDTO, Product.class);
        Optional<Product> prodFromDBByName = prodFromDTO.getName()!= null?
                productRepository.findByName(prodFromDTO.getName()): Optional.empty();
        Optional<Product> prodFromDBById = productDTO.getProductId()!= null?
                productRepository.findByProductId(productDTO.getProductId()): Optional.empty();
        if(prodFromDBByName.isPresent() || prodFromDBById.isPresent()) {
            prepareProductForAddOrUpdate(prodFromDTO);
            return Optional.of(modelMapper.map(productRepository.save(prodFromDTO), ProductDTO.class));
        }else{
            return Optional.empty();
        }
    }

    public Optional<ProductDTO> deleteProductById(long productId){
        Optional<Product> prodFromDB = productRepository.findByProductId(productId);
        if(prodFromDB.isPresent()) {
            productRepository.delete(prodFromDB.get());
            return prodFromDB.map(product -> modelMapper.map(product, ProductDTO.class));
        }else{
            return Optional.empty();
        }
    }

    public Optional<ProductDTO> deleteProductByName(String productName){
        Optional<Product> prodFromDB = productRepository.findByName(productName);
        if(prodFromDB.isPresent()) {
            productRepository.delete(prodFromDB.get());
            return prodFromDB.map(product -> modelMapper.map(product, ProductDTO.class));
        }else{
            return Optional.empty();
        }
    }

    private ProductDTO toDTO(Product p){
        return modelMapper.map(p, ProductDTO.class);
    }

}
