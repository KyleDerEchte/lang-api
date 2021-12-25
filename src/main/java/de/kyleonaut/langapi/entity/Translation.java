package de.kyleonaut.langapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author kyleonaut
 * @version 1.0.0
 * created at 25.12.2021
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Translation {
    private String key;
    private String content;
    private String language;
}
