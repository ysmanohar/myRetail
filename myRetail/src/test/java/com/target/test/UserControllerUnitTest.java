package com.target.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.target.config.WebConfig;
import com.target.controller.UserController;
import com.target.filter.CORSFilter;
import com.target.model.Price;
import com.target.model.Product;
import com.target.service.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.Arrays;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebConfig.class})
public class UserControllerUnitTest {

    private MockMvc mockMvc;

    @Mock
    private ProductService productService;

    @InjectMocks
    private UserController userController;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(userController)
                .addFilters(new CORSFilter())
                .build();
    }

    // =========================================== Get All Users ==========================================

    @Test
    public void test_get_all_success() throws Exception {
        List<Product> products = Arrays.asList(
                new Product(1, "Daenerys Targaryen",new Price(23.89,"USD")),
                new Product(2, "John Snow",new Price(18.89,"USD")));

        when(productService.getAll()).thenReturn(products);

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].productname", is("Daenerys Targaryen")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].productname", is("John Snow")));

        verify(productService, times(1)).getAll();
        verifyNoMoreInteractions(productService);
    }

    // =========================================== Get User By ID =========================================

    @Test
    public void test_get_by_id_success() throws Exception {
        Product product = new Product(1, "Daenerys Targaryen",new Price(28.19,"USD"));

        when(productService.findById(1)).thenReturn(product);

        mockMvc.perform(get("/products/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.productname", is("Daenerys Targaryen")));

        verify(productService, times(1)).findById(1);
        verifyNoMoreInteractions(productService);
    }

    @Test
    public void test_get_by_id_fail_404_not_found() throws Exception {
        when(productService.findById(1)).thenReturn(null);

        mockMvc.perform(get("/products/{id}", 1))
                .andExpect(status().isNotFound());

        verify(productService, times(1)).findById(1);
        verifyNoMoreInteractions(productService);
    }
    
    
 // =========================================== Get Product Name ===================================
    
    @Test
    public void test_get_by_productname_success() throws Exception {
        String product = "apple";

        when(productService.getproductname()).thenReturn(product);

        mockMvc.perform(get("/products/getproductname"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is("apple")));
               

        verify(productService, times(1)).getproductname();
        verifyNoMoreInteractions(productService);
    }

    // =========================================== Update Existing User ===================================

    @Test
    public void test_update_user_success() throws Exception {
        Product product = new Product(1, "Arya Stark",new Price(5.89,"INR"));

        when(productService.findById(product.getId())).thenReturn(product);
        doNothing().when(productService).update(product);

        mockMvc.perform(
                put("/products/{id}", product.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(product)))
                .andExpect(status().isOk());

        verify(productService, times(1)).findById(product.getId());
        verify(productService, times(1)).update(product);
        verifyNoMoreInteractions(productService);
    }

    @Test
    public void test_update_user_fail_404_not_found() throws Exception {
        Product product = new Product(999, "user not found",new Price(3.89,"USD"));

        when(productService.findById(product.getId())).thenReturn(null);

        mockMvc.perform(
                put("/products/{id}", product.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(product)))
                .andExpect(status().isNotFound());

        verify(productService, times(1)).findById(product.getId());
        verifyNoMoreInteractions(productService);
    }

    // =========================================== Delete User ============================================

    @Test
    public void test_delete_user_success() throws Exception {
        Product product = new Product(1, "Arya Stark",new Price(13.89,"USD"));

        when(productService.findById(product.getId())).thenReturn(product);
        doNothing().when(productService).delete(product.getId());

        mockMvc.perform(
                delete("/products/{id}", product.getId()))
                .andExpect(status().isOk());

        verify(productService, times(1)).findById(product.getId());
        verify(productService, times(1)).delete(product.getId());
        verifyNoMoreInteractions(productService);
    }

    @Test
    public void test_delete_user_fail_404_not_found() throws Exception {
        Product product = new Product(999, "user not found",new Price(33.89,"USD"));

        when(productService.findById(product.getId())).thenReturn(null);

        mockMvc.perform(
                delete("/products/{id}", product.getId()))
                .andExpect(status().isNotFound());

        verify(productService, times(1)).findById(product.getId());
        verifyNoMoreInteractions(productService);
    }

    // =========================================== CORS Headers ===========================================

    @Test
    public void test_cors_headers() throws Exception {
        mockMvc.perform(get("/products"))
                .andExpect(header().string("Access-Control-Allow-Origin", "*"))
                .andExpect(header().string("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE"))
                .andExpect(header().string("Access-Control-Allow-Headers", "*"))
                .andExpect(header().string("Access-Control-Max-Age", "3600"));
    }

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
