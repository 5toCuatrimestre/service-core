package jbar.service_core.Category.Controller;

import jbar.service_core.Category.Model.Category;
import jbar.service_core.Category.Model.CategoryDTO;
import jbar.service_core.Category.Model.CategoryRepository;
import jbar.service_core.Util.Enum.Status;
import jbar.service_core.Util.Enum.TypesResponse;
import jbar.service_core.Util.Response.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CategoryService {
    private final Logger log = LoggerFactory.getLogger(CategoryService.class);
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Message> findAll() {
        List<Category> categories = categoryRepository.findAll();
        log.info("All categories retrieved successfully");
        return new ResponseEntity<>(new Message(categories, "Categories retrieved", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Message> findById(Integer id) {
        Optional<Category> category = categoryRepository.findById(id);
        return category.map(value -> {
            log.info("Category with id {} retrieved successfully", id);
            return new ResponseEntity<>(new Message(value, "Category found", TypesResponse.SUCCESS), HttpStatus.OK);
        }).orElseGet(() -> {
            log.warn("Category with id {} not found", id);
            return new ResponseEntity<>(new Message(null, "Category not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        });
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<Message> create(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setName(categoryDTO.getName());
        category.setStatus(Status.ACTIVE);
        category.setCreatedAt(categoryDTO.getCreatedAt());

        categoryRepository.save(category);

        log.info("Category created successfully: {}", category);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new Message(category, "Category created", TypesResponse.SUCCESS));
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<Message> update(Integer id, CategoryDTO categoryDTO) {
        Optional<Category> existingCategoryOptional = categoryRepository.findById(id);

        if (existingCategoryOptional.isPresent()) {
            Category category = existingCategoryOptional.get();
            category.setName(categoryDTO.getName());
            category.setStatus(categoryDTO.getStatus());
            category.setUpdatedAt(LocalDateTime.now());

            categoryRepository.saveAndFlush(category); // Guarda y refresca en la BD

            log.info("Category with id {} updated successfully", id);
            return ResponseEntity.ok(new Message(category, "Category updated", TypesResponse.SUCCESS));
        }

        log.warn("Category with id {} not found for update", id);
        return new ResponseEntity<>(new Message(null, "Category not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<Message> changeStatus(Integer id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isPresent()) {
            Category existingCategory = category.get();
            if (existingCategory.getStatus() == Status.ACTIVE) {
                existingCategory.setStatus(Status.INACTIVE);
                existingCategory.setDeletedAt(LocalDateTime.now());
            } else {
                existingCategory.setStatus(Status.ACTIVE);
                existingCategory.setDeletedAt(null);
            }
            categoryRepository.save(existingCategory);
            log.info("Category with id {} status changed", id);
            return new ResponseEntity<>(new Message(null, "Category status changed", TypesResponse.SUCCESS), HttpStatus.OK);
        } else {
            log.warn("Category with id {} not found for status change", id);
            return new ResponseEntity<>(new Message(null, "Category not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }
    }
}