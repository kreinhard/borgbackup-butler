package de.micromata.borgbutler.server.logging;

import org.apache.commons.lang3.ClassUtils;
import org.apache.log4j.spi.LocationInfo;
import org.apache.log4j.spi.LoggingEvent;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * For easier serialization: JSON
 */
public class LoggingEventData implements Cloneable {
    private SimpleDateFormat ISO_DATEFORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    int orderNumber;
    LogLevel level;
    String message;
    private String messageObjectClass;
    private String loggerName;
    private String logDate;
    String javaClass;
    private String javaClassSimpleName;
    private String lineNumber;
    private String methodName;
    private String stackTrace;

    LoggingEventData() {

    }

    public LoggingEventData(LoggingEvent event) {
        level = LogLevel.getLevel(event);
        message = event.getRenderedMessage();
        messageObjectClass = event.getMessage().getClass().toString();
        loggerName = event.getLoggerName();
        logDate = getIsoLogDate(event.timeStamp);
        LocationInfo info = event.getLocationInformation();
        Throwable throwable = event.getThrowableInformation() != null ? event.getThrowableInformation().getThrowable() : null;
        if (throwable != null) {
            StringWriter writer = new StringWriter();
            PrintWriter printWriter = new PrintWriter(writer);
            throwable.printStackTrace(printWriter);
            stackTrace = writer.toString();
        }
        if (info != null) {
            javaClass = info.getClassName();
            javaClassSimpleName = ClassUtils.getShortClassName(info.getClassName());
            lineNumber = info.getLineNumber();
            methodName = info.getMethodName();
        }
    }

    public LogLevel getLevel() {
        return level;
    }

    public String getMessage() {
        return message;
    }

    public String getMessageObjectClass() {
        return messageObjectClass;
    }

    public String getLoggerName() {
        return loggerName;
    }

    public String getLogDate() {
        return logDate;
    }

    public String getJavaClass() {
        return javaClass;
    }

    public String getJavaClassSimpleName() {
        return javaClassSimpleName;
    }

    public String getLineNumber() {
        return lineNumber;
    }

    public String getMethodName() {
        return methodName;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String getIsoLogDate(long millis) {
        synchronized (ISO_DATEFORMAT) {
            return ISO_DATEFORMAT.format(new Date(millis));
        }
    }

    @Override
    public Object clone() {
        LoggingEventData clone = null;
        try {
            clone = (LoggingEventData) super.clone();
        } catch (CloneNotSupportedException ex) {
            throw new UnsupportedOperationException(this.getClass().getCanonicalName() + " isn't cloneable: " + ex.getMessage(), ex);
        }
        return clone;
    }
}
