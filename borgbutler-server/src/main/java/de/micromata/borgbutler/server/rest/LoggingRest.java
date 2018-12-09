package de.micromata.borgbutler.server.rest;

import de.micromata.borgbutler.json.JsonUtils;
import de.micromata.borgbutler.server.logging.Log4jMemoryAppender;
import de.micromata.borgbutler.server.logging.LogFilter;
import de.micromata.borgbutler.server.logging.LogLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("/logging")
public class LoggingRest {
    private Logger log = LoggerFactory.getLogger(LoggingRest.class);

    /**
     * @param requestContext
     * @param search
     * @param logLevelTreshold fatal, error, warn, info, debug or trace (case insensitive).
     * @param maxSize          Max size of the result list.
     * @param ascendingOrder   Default is false (default is descending order).
     * @param lastReceivedOrderNumber The last received order number for updating log entries (preventing querying all entries again).
     * @param mdcTemplatePk
     * @param mdcTemplateDefinitionPk
     * @param prettyPrinter
     * @return
     */
    @GET
    @Path("query")
    @Produces(MediaType.APPLICATION_JSON)
    public String query(@Context HttpServletRequest requestContext,
                        @QueryParam("search") String search, @QueryParam("treshold") String logLevelTreshold,
                        @QueryParam("maxSize") Integer maxSize, @QueryParam("ascendingOrder") Boolean ascendingOrder,
                        @QueryParam("lastReceivedOrderNumber") Integer lastReceivedOrderNumber,
                        @QueryParam("mdcTemplatePk") String mdcTemplatePk,
                        @QueryParam("mdcTemplateDefinitionPk") String mdcTemplateDefinitionPk,
                        @QueryParam("prettyPrinter") boolean prettyPrinter) {
        LogFilter filter = new LogFilter();
        filter.setSearch(search);
        if (logLevelTreshold != null) {
            try {
                LogLevel treshold = LogLevel.valueOf(logLevelTreshold.trim().toUpperCase());
                filter.setThreshold(treshold);
            } catch (IllegalArgumentException ex) {
                log.error("Can't parse log level treshold: " + logLevelTreshold + ". Supported values (case insensitive): " + LogLevel.getSupportedValues());
            }
        }
        if (filter.getThreshold() == null) {
            filter.setThreshold(LogLevel.INFO);
        }
        if (maxSize != null) {
            filter.setMaxSize(maxSize);
        }
        if (ascendingOrder != null && ascendingOrder == true) {
            filter.setAscendingOrder(true);
        }
        if (lastReceivedOrderNumber != null) {
            filter.setLastReceivedLogOrderNumber(lastReceivedOrderNumber);
        }
        Log4jMemoryAppender appender = Log4jMemoryAppender.getInstance();
        String json = JsonUtils.toJson(appender.query(filter, RestUtils.getUserLocale(requestContext)), prettyPrinter);
        return json;
    }
}
