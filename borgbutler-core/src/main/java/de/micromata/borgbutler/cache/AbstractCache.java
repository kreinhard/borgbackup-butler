package de.micromata.borgbutler.cache;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.micromata.borgbutler.json.JsonUtils;
import lombok.Getter;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractCache<T> {
    private static Logger log = LoggerFactory.getLogger(AbstractCache.class);

    @JsonIgnore
    protected File cacheFile;
    @Getter
    @JsonProperty
    private List<T> elements = new ArrayList<>();

    public T get(String identifier) {
        if (identifier == null) {
            return null;
        }
        for (T element : elements) {
            if (matches(element, identifier)) {
                return element;
            }
        }
        return null;
    }

    public abstract boolean matches(T element, String identifier);

    public abstract String getIdentifier(T element);

    public abstract void updateFrom(T dest, T source);

    public void upsert(T element) {
        T existingElement = get(getIdentifier(element));
        if (existingElement == null) {
            elements.add(element);
        } else {
            updateFrom(existingElement, element);
        }
    }

    public void read() {
        try {
            String json = FileUtils.readFileToString(cacheFile, Charset.forName("UTF-8"));
            AbstractCache readCache = JsonUtils.fromJson(this.getClass(), json);
            if (readCache != null) {
                this.elements = readCache.elements;
            } else {
                log.error("Error while parsing cache: " + cacheFile.getAbsolutePath());
            }
        } catch (IOException ex) {
            log.error("Error while trying to read cache file '" + cacheFile.getAbsolutePath() + "': "
                    + ex.getMessage(), ex);
        }
    }

    public void save() {
        log.info("Saving to cache file: " + cacheFile);
        String json = JsonUtils.toJson(this);
        try {
            FileUtils.write(cacheFile, json, Charset.forName("UTF-8"));
        } catch (IOException ex) {
            log.error("Error while trying to write repos cache file '" + cacheFile.getAbsolutePath() + "': "
                    + ex.getMessage(), ex);
        }
    }

    /**
     * Needed by jackson for deserialization.
     */
    AbstractCache() {
    }

    AbstractCache(File cacheDir, String cacheFilename) {
        cacheFile = new File(cacheDir, cacheFilename);
    }
}
