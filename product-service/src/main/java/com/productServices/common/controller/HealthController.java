package com.productServices.common.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/api/products/")
@RestController
public class HealthController {

    @GetMapping("e-com")
    public String helloWorld(){
        return "Fardeen is Here";
    }

    public ResponseEntity<Map<String,String>> headers(
            @RequestHeader("X-User-Id") String userId,
            @RequestHeader("X-User-Email") String email,
            @RequestHeader("X-User-Role") String role) {

        Map<String,String> map = new HashMap<>();
        map.put("userId", userId);
        map.put("email", email);
        map.put("role", role);

        return ResponseEntity.ok(map);
    }

//    @Transactional ensures each operation is atomic.
//
//    | Area            | HTTP   | Path                      | Description                                                  |
//            | --------------- | ------ | ------------------------- | ------------------------------------------------------------ |
//            | Product catalog | POST   | /api/products             | Create a product (admin).   Done                                 |
//            | Product catalog | GET    | /api/products/{id}        | Get product details.        Done                                 |
//            | Product catalog | GET    | /api/products             | Search list with filters, pagination.                        |
//            | Product catalog | PUT    | /api/products/{id}        | Update product details (admin).                              |
//            | Product catalog | DELETE | /api/products/{id}        | Soft delete/disable product (admin).                         |
//            | Product images  | POST   | /api/products/{id}/images | Upload image(s) (S3 later). github                           |
//            | Product images  | GET    | /api/products/{id}/images | List image URLs.                                             |
//            | Product search  | GET    | /api/products/search      | Advanced search / ElasticSearch integration (future). github |

//    CreateProductDTO – request body for create (input).
//    ProductDTO – list view (id, name, description, price).
//    ProductResponseDTO – detail view (id, name, desc, price, categoryName).
}
