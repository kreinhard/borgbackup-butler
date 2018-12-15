package de.micromata.borgbutler.json.borg;

import de.micromata.borgbutler.json.JsonUtils;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * This object is given by <tt>borg list repo</tt>.
 */
public class BorgArchive implements Serializable, Comparable<BorgArchive> {
    private static final long serialVersionUID = -7872260170265536732L;
    @Getter
    private String archive;
    @Getter
    private String barchive;
    @Getter
    private String id;
    @Getter
    private String name;
    @Getter
    @Setter
    private String start;
    @Getter
    @Setter
    private String time;

    /**
     * In reverse order, compares times.
     * @param o
     * @return
     */
    @Override
    public int compareTo(BorgArchive o) {
        // Reverse order:
        return StringUtils.compare(o.time, this.time);
    }

    public String toString() {
        return JsonUtils.toJson(this, true);
    }
}