package jb.workclock.api.v1.resources;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import jb.workclock.lib.EventType;
import jb.workclock.services.beans.EventTypeBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.logging.Logger;


@ApplicationScoped
@Path("/event-types")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EventTypeResource {

    private Logger log = Logger.getLogger(EventTypeResource.class.getName());

    @Inject
    private EventTypeBean eventTypeBean;


    @Context
    protected UriInfo uriInfo;

    @Operation(description = "Get all Event types.", summary = "Get all Event types")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "List of Event types",
                    content = @Content(schema = @Schema(implementation = EventType.class, type = SchemaType.ARRAY)),
                    headers = {@Header(name = "X-Total-Count", description = "Number of objects in list")}
            )})
    @GET
    public Response getEventTypes() {
        List<EventType> eventType = eventTypeBean.getEventTypeFilter(uriInfo);
        return Response.status(Response.Status.OK).entity(eventType).build();
    }


    @Operation(description = "Get Event type by ID.", summary = "Get Event type by ID")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Event type retrieved successfully.",
                    content = @Content(
                            schema = @Schema(implementation = EventType.class))
            ),
            @APIResponse(responseCode = "404",
                    description = "Event type not found."
            )
    })
    @GET
    @Path("/{eventTypeId}")
    public Response getEventType(@Parameter(description = "Event type ID.", required = true)
                                     @PathParam("eventTypeId") Integer eventTypeId) {

        EventType eventType = eventTypeBean.getEventType(eventTypeId);

        if (eventType == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(eventType).build();
    }

    @Operation(description = "Create Event type.", summary = "Create Event type")
    @APIResponses({
            @APIResponse(responseCode = "201",
                    description = "Event type successfully added."
            ),
            @APIResponse(responseCode = "400", description = "Bad request or validation error.")
    })
    @POST
    public Response createEventType(@RequestBody(
            description = "DTO object with image Event type.",
            required = true, content = @Content(
            schema = @Schema(implementation = EventType.class))) EventType eventType) {

        if ((eventType.title == null || eventType.isPaid == null)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        else {
            eventType = eventTypeBean.createEventType(eventType);
        }

        return Response.status(Response.Status.CONFLICT).entity(eventType).build();

    }


    @Operation(description = "Update Event type by ID.", summary = "Update Event type by ID")
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Event type successfully updated."
            ),
            @APIResponse(
                    responseCode = "404",
                    description = "Event type not found."
            )
    })
    @PUT
    @Path("{eventTypeId}")
    public Response putEventType(@Parameter(description = "Event type ID.", required = true)
                                     @PathParam("eventTypeId") Integer eventTypeId,
                                     @RequestBody(
                                             description = "DTO object with image Event type.",
                                             required = true, content = @Content(
                                             schema = @Schema(implementation = EventType.class)))
                                             EventType eventType){

        eventType = eventTypeBean.putEventType(eventTypeId, eventType);

        if (eventType == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.NOT_MODIFIED).build();

    }

    @Operation(description = "Delete Event type by ID.", summary = "Delete Event type by ID")
    @APIResponses({
            @APIResponse(
                    responseCode = "204",
                    description = "Event type successfully deleted."
            ),
            @APIResponse(
                    responseCode = "404",
                    description = "Event type not found."
            )
    })
    @DELETE
    @Path("{eventTypeId}")
    public Response deleteEventType(@Parameter(description = "Event type ID.", required = true)
                                        @PathParam("eventTypeId") Integer eventTypeId){

        boolean deleted = eventTypeBean.deleteEventType(eventTypeId);

        if (deleted) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}