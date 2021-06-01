package org.github.jfdelolmo.reactor.sec01.assignement;

import org.github.jfdelolmo.reactor.common.Common;

import java.util.concurrent.atomic.AtomicReference;

public class Main {
    public static void main(String[] args) {
        FilesService filesService = new FilesService();
        AtomicReference<String> content = new AtomicReference<>();
        filesService.read("demo01.txt")
                .subscribe(content::set, Common.onError(), Common.onComplete());

        System.out.println(content.get());

        filesService.write("demo02.txt", "This is demo02 file content")
                .subscribe(Common.onNext(), Common.onError(), () -> System.out.println("File created"));

        filesService.write("demo03.txt", "This is will be deleted")
                .subscribe(Common.onNext(), Common.onError(), () -> System.out.println("File created"));

        filesService.delete("demo03.txt")
                .subscribe(Common.onNext(), Common.onError(), () -> System.out.println("File deleted"));
    }
}
