package com.barelyconscious.jacket;

import com.barelyconscious.jacket.guice.*;
import com.google.inject.*;
import lombok.extern.log4j.*;
import picocli.*;

import java.util.*;

import static com.google.inject.Guice.*;

@Log4j2
public class Main {

    public static void main(String[] args) {
        Injector injector = createInjector(new AppModule());
        var commandLine = injector.getInstance(CommandLine.class);

        final String[] _args = new String[] {
            "task",
            "v",
            "-d",
            "2019-01-01"
//            "d-10m4y-3"
        };
        System.out.println("$ jacket " + Arrays.toString(_args).replace(", ", " ").replace("[", "").replace("]", ""));
        commandLine.execute(_args);
    }
}
