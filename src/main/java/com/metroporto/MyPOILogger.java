//package com.metroporto;
//
//import org.apache.logging.log4j.Level;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.core.LoggerContext;
//import org.apache.logging.log4j.core.config.Configuration;
//import org.apache.logging.log4j.core.config.LoggerConfig;
//import org.apache.logging.log4j.core.config.AppenderRef;
//import org.apache.logging.log4j.core.config.ConfigurationFactory;
//
//public class MyPOILogger
//{
//
//    public static void configure() {
//        LoggerContext loggerContext = (LoggerContext) LogManager.getContext(false);
//        Configuration configuration = loggerContext.getConfiguration();
//
//        // Create an appender ref
//        AppenderRef appenderRef = AppenderRef.createAppenderRef("YourAppenderName", null, null);
//
//        // Create a logger config for org.apache.poi with error level and the appender ref
//        LoggerConfig loggerConfig = LoggerConfig.createLogger(false, Level.ERROR, "org.apache.poi", "false", new AppenderRef[] { appenderRef }, null, configuration, null);
//        configuration.addLogger("org.apache.poi", loggerConfig);
//
//        // Create a root logger config with error level
//        // Create a root logger config with error level
//        LoggerConfig rootLoggerConfig = configuration.getRootLogger();
//        rootLoggerConfig.setLevel(Level.ERROR);
//        rootLoggerConfig.getAppenderRefs().clear();
//        rootLoggerConfig.getAppenderRefs().add(appenderRef);
//
//        // Update the configuration
//        loggerContext.updateLoggers(configuration);
//    }
//}
