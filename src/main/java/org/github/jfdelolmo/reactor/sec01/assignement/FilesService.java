package org.github.jfdelolmo.reactor.sec01.assignement;

import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FilesService {

    private static final Path BASE_PATH = Paths.get("src/main/resources/sec01/assignement");

    public Mono<String> read(String fileName) {
        return Mono.fromSupplier(()->readFile(fileName));
    }

    public Mono<Void> write(String fileName, String content) {
        return Mono.fromRunnable(()->writeFile(fileName, content));
    }

    public Mono<Void> delete(String fileName) {
        return Mono.fromRunnable(()->deleteFile(fileName));
    }

    private static String readFile(String fileName){
        try {
            return Files.readString(BASE_PATH.resolve(fileName));
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private void writeFile(String fileName, String content){
        try {
            Files.writeString(BASE_PATH.resolve(fileName), content);
        }catch (IOException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    private void deleteFile(String fileName){
        try {
            Files.delete(BASE_PATH.resolve(fileName));
        }catch (IOException e){
            throw new RuntimeException(e.getMessage());
        }
    }

//    public Mono<Void> deleteFile(String fileName){
//            return Mono.fromRunnable(() ->
//                    {
//                        try {
//                            Files.delete(BASE_PATH.resolve(fileName));
//                        } catch (IOException e) {
//                            //e.printStackTrace();
//                            throw new RuntimeException(e.getMessage());
//                        }
//                    }
//            );
//    }

}
