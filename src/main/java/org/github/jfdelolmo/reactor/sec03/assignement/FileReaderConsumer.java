package org.github.jfdelolmo.reactor.sec03.assignement;

import org.github.jfdelolmo.reactor.common.Common;

import java.io.FileNotFoundException;
import java.nio.file.Paths;

public class FileReaderConsumer {

    public static void main(String[] args) throws FileNotFoundException {
        FileReaderService fileReaderService = new FileReaderService();
        fileReaderService
                .read(Paths.get("Alice.txt"))
//                .take(10)
                .subscribe(Common.subscriber());
    }
}
