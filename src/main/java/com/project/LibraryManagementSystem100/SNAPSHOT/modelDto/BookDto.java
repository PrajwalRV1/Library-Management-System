package com.project.LibraryManagementSystem100.SNAPSHOT.modelDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BookDto {

    private String title;
    private String author;
    private Integer quantity;
}
