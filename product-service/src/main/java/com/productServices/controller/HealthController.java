package com.productServices.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping("e-com")
    public String helloWorld(){
        return "Fardeen is Here";
    }
//
//    | Area            | HTTP   | Path                      | Description                                                  |
//            | --------------- | ------ | ------------------------- | ------------------------------------------------------------ |
//            | Product catalog | POST   | /api/products             | Create a product (admin).                                    |
//            | Product catalog | GET    | /api/products/{id}        | Get product details.                                         |
//            | Product catalog | GET    | /api/products             | Search list with filters, pagination.                        |
//            | Product catalog | PUT    | /api/products/{id}        | Update product details (admin).                              |
//            | Product catalog | DELETE | /api/products/{id}        | Soft delete/disable product (admin).                         |
//            | Product images  | POST   | /api/products/{id}/images | Upload image(s) (S3 later). github                           |
//            | Product images  | GET    | /api/products/{id}/images | List image URLs.                                             |
//            | Product search  | GET    | /api/products/search      | Advanced search / ElasticSearch integration (future). github |
}
