package de.micromata.borgbutler.data;

import de.micromata.borgbutler.json.borg.BorgArchiveLimits;
import de.micromata.borgbutler.json.borg.BorgArchiveStats;
import de.micromata.borgbutler.json.borg.BorgCache;
import de.micromata.borgbutler.json.borg.BorgEncryption;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.List;

/**
 *
 */
public class Archive implements Serializable, Comparable<Archive> {
    /**
     * For convenience purposes for the client.
     */
    @Getter
    @Setter
    private String repoName;
    /**
     * For convenience purposes for the client.
     */
    @Getter
    @Setter
    private String repoDisplayName;
    /**
     * For convenience purposes for the client.
     */
    @Getter
    @Setter
    private String repoId;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String id;
    @Getter
    @Setter
    private BorgCache cache;
    @Getter
    @Setter
    private BorgEncryption encryption;

    @Getter
    @Setter
    private int[] chunkerParams;
    /**
     * The command line used for creating this archive: borg create --filter...
     */
    @Getter
    @Setter
    private String[] commandLine;
    @Getter
    @Setter
    private String comment;
    @Getter
    @Setter
    private String start;
    @Getter
    @Setter
    private String end;
    @Getter
    @Setter
    private String time;
    @Getter
    @Setter
    private String duration;
    @Getter
    @Setter
    private BorgArchiveStats stats;
    @Getter
    @Setter
    private BorgArchiveLimits limits;
    @Getter
    @Setter
    private String username;
    @Getter
    @Setter
    private String hostname;
    /**
     * For comparing functionality.
     */
    @Getter
    @Setter
    private List<ArchiveShortInfo> archiveShortInfoList;
    /**
     * Is the file list of this archive loaded and available in Butler's cache.
     */
    @Getter
    @Setter
    private boolean fileListAlreadyCached;

    /**
     *
     * @return repoName::archiveName
     */
    public String getBorgIdentifier() {
        return repoName + "::" + name;
    }

    /**
     * Is <tt>borg info repo::archive</tt> already called for this archive?
     *
     * @return true, if borg info was called, otherwise false.
     */
    public boolean hasInfoData() {
        return commandLine != null && commandLine.length > 0;
    }

    /**
     * In reverse order, compares times.
     *
     * @param o
     * @return
     */
    @Override
    public int compareTo(Archive o) {
        // Reverse order:
        return StringUtils.compare(o.time, this.time);
    }
}
