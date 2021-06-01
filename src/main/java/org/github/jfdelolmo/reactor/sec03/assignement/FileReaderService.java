package org.github.jfdelolmo.reactor.sec03.assignement;

import reactor.core.publisher.Flux;
import reactor.core.publisher.SynchronousSink;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class FileReaderService {

    private static final Path BASE_PATH = Paths.get("src/main/resources/sec03/assignement");

    public Flux<String> readFile(String fileName) throws FileNotFoundException {
        BufferedReader reader = new BufferedReader(new FileReader(BASE_PATH.resolve(fileName).toString()));
        return Flux.generate(
                () -> 1,
                (counter, sink) -> {
                    String readLine = null;
                    try {
                        readLine = reader.readLine();
                    } catch (IOException e) {
                        sink.error(e.getCause());
                    }
                    if (readLine == null) {
                        sink.complete();
                        try {
                            reader.close();
                        } catch (IOException e) {
                            sink.error(e);
                        }
                    } else {
                        sink.next(readLine);
                    }
                    return counter + 1;
                }
//                stateConsumer -> {}
        );
    }

    private Callable<BufferedReader> openReader(Path path){
        return () -> Files.newBufferedReader(BASE_PATH.resolve(path));
    }

    private BiFunction<BufferedReader, SynchronousSink<String>, BufferedReader> read(){
        return (br, synk)->{
            try {
                String line = br.readLine();
                if (Objects.isNull(line)){
                    synk.complete();
                }else{
                    synk.next(line);
                }
            } catch (IOException e) {
                synk.error(e);
            }
            return br;
        };
    }

    private Consumer<BufferedReader> closeReader(){
        return br -> {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }

    public Flux<String> read(Path path){
        return Flux.generate(
                openReader(path),
                read(),
                closeReader()
        );
    }

}
