package com.barelyconscious.jacket.guice;

import com.barelyconscious.jacket.cli.*;
import com.barelyconscious.jacket.data.*;
import com.barelyconscious.jacket.data.impl.*;
import com.google.inject.*;
import picocli.*;

public class AppModule extends AbstractModule {

    @Provides
    @Singleton
    CommandLine providesCommandLine(final JacketCLI jacketCLI) {
        var commandLine = new CommandLine(jacketCLI);

        commandLine.setAbbreviatedSubcommandsAllowed(true);
        commandLine.setAbbreviatedOptionsAllowed(true);

        return commandLine;
    }

    @Provides
    @Singleton
    JacketCLI providesJacketCLI() {
        return new JacketCLI();
    }

    @Provides
    @Singleton
    JacketDAL providesJacketDAL() {
        return new FileSystemJacketDAL();
    }
}
