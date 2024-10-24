package org.example;

import lombok.Data;

@Data
public class Book {
    private String name;
    private String author;
    private Integer publishingYear;
    private String isbn;
    private String publisher;
}
