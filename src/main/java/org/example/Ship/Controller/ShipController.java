package org.example.Ship.Controller;

import org.example.Ship.DTO.GetShipsResponse;
import org.example.Ship.Service.ShipService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("")
public class ShipController {
    private ShipService shipService;

    public ShipController() {
    }

    @Inject
    public void setService(ShipService shipService) {
        this.shipService = shipService;
    }

    @GET
    @Path("/ships")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getShips() {
        return Response
                .ok(GetShipsResponse.entityToDtoMapper().apply(shipService.findAll()))
                .build();
    }
}
