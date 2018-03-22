package ru.intervale.calculator.file;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileResource implements FileReader, FileWriter {

    private final Path path;

    public FileResource(String fileName) {
        try {
            this.path = getPath(fileName);
        } catch (URISyntaxException e) {
            throw new IORuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public List<String> toList() {
        try (Stream<String> stream = Files.lines(path)) {
            return stream.collect(Collectors.toList());
        } catch (IOException e) {
            throw new IORuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public void write(List<String> list) {
        try {
            Files.write(this.path, list);
        } catch (IOException e) {
            throw new IORuntimeException(e.getMessage(), e);
        }
    }

    private Path getPath(String fileName) throws URISyntaxException {
        final URL url = getUrl(fileName);
        return Paths.get(url.toURI());
    }

    private URL getUrl(String fileName) {
        return Objects.requireNonNull(getClass().getClassLoader().getResource(fileName));
    }
}
