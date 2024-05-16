package com.project.LibraryManagementSystem100.SNAPSHOT.modelDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class FineDto {

    private String username;
    private Integer amount;
}
