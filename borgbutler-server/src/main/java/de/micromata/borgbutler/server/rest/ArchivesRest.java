package de.micromata.borgbutler.server.rest;

import de.micromata.borgbutler.cache.ButlerCache;
import de.micromata.borgbutler.data.Archive;
import de.micromata.borgbutler.data.Repository;
import de.micromata.borgbutler.json.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/archives")
public class ArchivesRest {
    private static Logger log = LoggerFactory.getLogger(ArchivesRest.class);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    /**
     *
     * @param repo Name of repository ({@link Repository#getName()}.
     * @param archive Id or name of archive.
     * @param prettyPrinter If true then the json output will be in pretty format.
     * @return Repository (including list of archives) as json string.
     * @see JsonUtils#toJson(Object, boolean)
     */
    public String getArchive(@QueryParam("repo") String repoName,
                             @QueryParam("archive") String archiveIdOrName, @QueryParam("force") boolean force,
                             @QueryParam("prettyPrinter") boolean prettyPrinter) {
        Archive archive = ButlerCache.getInstance().getArchive(repoName, archiveIdOrName, force);
        return JsonUtils.toJson(archive, prettyPrinter);
    }

    @GET
    @Path("filelist")
    @Produces(MediaType.APPLICATION_JSON)
    /**
     *
     * @param repo Name of repository ({@link Repository#getName()}.
     * @param archive Id or name of archive.
     * @param prettyPrinter If true then the json output will be in pretty format.
     * @return Repository (including list of archives) as json string.
     * @see JsonUtils#toJson(Object, boolean)
     */
    public String getArchiveFileLIst(@QueryParam("repo") String repoName,
                             @QueryParam("archive") String archiveIdOrName, @QueryParam("force") boolean force,
                             @QueryParam("prettyPrinter") boolean prettyPrinter) {
        Archive archive = ButlerCache.getInstance().getArchive(repoName, archiveIdOrName, force);
        return JsonUtils.toJson(archive, prettyPrinter);
    }
}