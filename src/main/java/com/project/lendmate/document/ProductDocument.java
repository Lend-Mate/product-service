package com.project.lendmate.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "products_search")
public class ProductDocument {

    @Id
    private String id;

    @Field(type = FieldType.Text, name = "productname")
    private String productName;

    @Field(type = FieldType.Text, name = "description")
    private String description;
}