//package com.lendmate.productservice.document;
//
//import lombok.*;
//import org.springframework.data.annotation.Id;
//import org.springframework.data.elasticsearch.annotations.Document;
//import org.springframework.data.elasticsearch.annotations.Field;
//import org.springframework.data.elasticsearch.annotations.FieldType;
//
//@Getter
//@Setter
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//@Document(indexName = "products_search")
//public class ProductDocument {
//
//    @Id
//    private String id;
//
//    @Field(type = FieldType.Integer, name = "category_id")
//    private String categoryId;
//
//    @Field(type = FieldType.Text, name = "product_name")
//    private String productName;
//
//    @Field(type = FieldType.Text, name = "brand")
//    private String brand;
//
//    @Field(type = FieldType.Float, name = "price")
//    private String price;
//
//    @Field(type = FieldType.Integer, name = "min_rental_days")
//    private String minRentalDays;
//
//    @Field(type = FieldType.Integer, name = "max_rental_days")
//    private String maxRentalDays;
//
//    @Field(type = FieldType.Text, name = "description")
//    private String description;
//}