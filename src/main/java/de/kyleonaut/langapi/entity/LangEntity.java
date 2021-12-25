package de.kyleonaut.langapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author kyleonaut
 * @version 1.0.0
 * created at 25.12.2021
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LangEntity {
    private String key;
    private List<Translation> translations;
}
