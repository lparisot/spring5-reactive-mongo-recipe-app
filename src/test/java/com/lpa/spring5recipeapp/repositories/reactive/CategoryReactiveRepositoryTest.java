package com.lpa.spring5recipeapp.repositories.reactive;

import com.lpa.spring5recipeapp.domain.Category;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@DataMongoTest
public class CategoryReactiveRepositoryTest {
    public static final String FOO = "Foo";

    @Autowired
    private CategoryReactiveRepository categoryReactiveRepository;

    @Before
    public void setUp() throws Exception {
        categoryReactiveRepository.deleteAll().block();
    }

    @Test
    public void testCategorySave() throws Exception {
        Category category = new Category();
        category.setDescription(FOO);

        categoryReactiveRepository.save(category).block();

        Long count = categoryReactiveRepository.count().block();
        assertEquals(Long.valueOf(1L), count);
    }

    @Test
    public void findCategoryByDescription() throws Exception {
        Category category = new Category();
        category.setDescription(FOO);

        categoryReactiveRepository.save(category).then().block();

        Category fetchedCategory = categoryReactiveRepository.findByDescription(FOO).block();

        assertNotNull(fetchedCategory.getId());
        assertEquals(FOO, fetchedCategory.getDescription());
    }

}