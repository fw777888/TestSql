package org.example.model;

import lombok.*;

@EqualsAndHashCode(exclude = {"name", "isHome"})
@AllArgsConstructor
@Builder
@Data
public class Dog {

    private Long id;
    private String name;
    private boolean isHome;
}
