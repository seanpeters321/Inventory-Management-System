package main.java;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


/**
 * <b>Class that searches for files</b>
 *
 * @author Sean Peters
 */
public class FileSearcher {

    /**
     * Searches through a given directory and outputs all files inside.
     *
     * @param path     directory to search in
     * @param consumer where the filepath goes
     */
    public static void fileFinder(Path path, Consumer<Path> consumer) {
        try (DirectoryStream<Path> newDirectoryStream = Files.newDirectoryStream(path)) {
            StreamSupport.stream(newDirectoryStream.spliterator(), false)
                    .peek(p -> {
                        consumer.accept(p);
                        if (p.toFile()
                                .isDirectory()) {
                            fileFinder(p, consumer);
                        }
                    })
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
