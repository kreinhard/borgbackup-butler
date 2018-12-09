package de.micromata.borgbutler.server.rest;

import de.micromata.borgbutler.json.JsonUtils;
import de.micromata.borgbutler.server.ServerConfiguration;
import de.micromata.borgbutler.server.ServerConfigurationHandler;
import de.micromata.borgbutler.server.user.UserData;
import de.micromata.borgbutler.server.user.UserManager;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/configuration")
public class ConfigurationRest {
    private Logger log = LoggerFactory.getLogger(ConfigurationRest.class);

    @GET
    @Path("config")
    @Produces(MediaType.APPLICATION_JSON)
    /**
     *
     * @param prettyPrinter If true then the json output will be in pretty format.
     * @see JsonUtils#toJson(Object, boolean)
     */
    public String getConfig(@QueryParam("prettyPrinter") boolean prettyPrinter) {
        ServerConfiguration config = new ServerConfiguration();
        config.copyFrom(ServerConfigurationHandler.getInstance().getConfiguration());
        String json = JsonUtils.toJson(config, prettyPrinter);
        return json;
    }

    @POST
    @Path("config")
    @Produces(MediaType.TEXT_PLAIN)
    public void setConfig(String jsonConfig) {
        ServerConfigurationHandler configurationHandler = ServerConfigurationHandler.getInstance();
        ServerConfiguration config = configurationHandler.getConfiguration();
        ServerConfiguration srcConfig = JsonUtils.fromJson(ServerConfiguration.class, jsonConfig);
        config.copyFrom(srcConfig);
        configurationHandler.save();
    }

    @GET
    @Path("user")
    @Produces(MediaType.APPLICATION_JSON)
    /**
     *
     * @param prettyPrinter If true then the json output will be in pretty format.
     * @see JsonUtils#toJson(Object, boolean)
     */
    public String getUser(@QueryParam("prettyPrinter") boolean prettyPrinter) {
        UserData user = RestUtils.getUser();
        String json = JsonUtils.toJson(user, prettyPrinter);
        return json;
    }

    @POST
    @Path("user")
    @Produces(MediaType.TEXT_PLAIN)
    public void setUser(String jsonConfig) {
        UserData user = JsonUtils.fromJson(UserData.class, jsonConfig);
        if (user.getLocale() != null && StringUtils.isBlank(user.getLocale().getLanguage())) {
            // Don't set locale with "" as language.
            user.setLocale(null);
        }
        if (StringUtils.isBlank(user.getDateFormat())) {
            // Don't set dateFormat as "".
            user.setDateFormat(null);
        }
        UserManager.instance().saveUser(user);
    }

    /**
     * Resets the settings to default values (deletes all settings).
     */
    @GET
    @Path("reset")
    @Produces(MediaType.APPLICATION_JSON)
    public String resetConfig(@QueryParam("IKnowWhatImDoing") boolean securityQuestion) {
        if (securityQuestion) {
            ServerConfigurationHandler.getInstance().removeAllSettings();
        }
        return getConfig(false);
    }
}
