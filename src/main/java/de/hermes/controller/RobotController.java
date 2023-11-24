package de.hermes.controller;

import de.hermes.entities.Job;
import de.hermes.requests.EnterPathRequest;
import de.hermes.services.RobotService;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/tibber-developer-test")
public class RobotController {

    @GET
    @Path("/hallo-welt")
    @Produces(MediaType.TEXT_PLAIN)
    public String halloWelt() {
        return "Hallo Welt";
    }

    @POST
    @Path("/enter-path")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @Transactional
    public Job enterPath(EnterPathRequest enterPathRequest) {
        var job = RobotService.createJobFromEnterPathRequest(enterPathRequest);
        job.persist();
        return job;
    }
}
