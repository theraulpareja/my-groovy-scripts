import java.text.SimpleDateFormat

/**
 * Possibility to log TRACE, DEBUG, INFO, WARN and ERROR levels
 * Simply call methods like info, debug etc. (case insensitive)
 * Possibility to set/change:
 * * logFile - location of a log file (default value:default.log)
 * * dateFormat - format of a date in log file(default value:dd.MM.yyyy;HH:mm:ss.SSS)
 * * printToConsole - whether a message should be printed to console as well (default value:false)
 * pavel.sklenar from https://blog.pavelsklenar.com/simple-groovy-logger-without-any-logging-framework/ is the 
 * real author but the ESS borrowed that bit of code thankfully :-) 
 *
 */
class Logger {
    private File logFile = new File("/tmp/default.log")
    private String dateFormat = "dd.MM.yyyy;HH:mm:ss.SSS"
    private boolean printToConsole = false

    /**
     * Catch all defined logging levels, throw  MissingMethodException otherwise
     * @param name
     * @param args
     * @return
     */
    def methodMissing(String name, args) {
        def messsage = args[0]
        if (printToConsole) {
            println messsage
        }
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat)
        String date = formatter.format(new Date())
        switch (name.toLowerCase()) {
            case "trace":
                logFile << "${date} TRACE ${messsage}\n"
                break
            case "debug":
                logFile << "${date} DEBUG ${messsage}\n"
                break
            case "info":
                logFile << "${date} INFO  ${messsage}\n"
                break
            case "warn":
                logFile << "${date} WARN  ${messsage}\n"
                break
            case "error":
                logFile << "${date} ERROR ${messsage}\n"
                break
            default:
                throw new MissingMethodException(name, delegate, args)
        }
    }
}

def logger = new Logger()
logger.trace "Trace level test"
logger.debug "Debug level test"
logger.info "Info level test"
logger.warn "Warn level test"
logger.error "Error level test"
