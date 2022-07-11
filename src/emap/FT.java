package emap;

import java.nio.file.Path;
import java.util.Optional;

public interface FT {
    Optional<String> tree(Path path);
}
