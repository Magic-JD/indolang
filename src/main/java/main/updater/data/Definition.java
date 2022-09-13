package main.updater.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

@Getter
@AllArgsConstructor
public class Definition {

    private final Set<String> englishWords;
    private final Set<String> indonesianWords;

}
