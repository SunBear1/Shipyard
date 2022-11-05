package org.example.Harbor.Controller;

import org.example.Harbor.DTO.CreateHarborRequest;
import org.example.Harbor.DTO.GetHarborResponse;
import org.example.Harbor.DTO.GetHarborsResponse;
import org.example.Harbor.DTO.UpdateHarborRequest;
import org.example.Harbor.Entity.Harbor;
import org.example.Harbor.Service.HarborService;
import org.example.Ship.DTO.CreateShipRequest;
import org.example.Ship.DTO.GetShipResponse;
import org.example.Ship.DTO.GetShipsResponse;
import org.example.Ship.DTO.UpdateShipRequest;
import org.example.Ship.Entity.Ship;
import org.example.Ship.Service.ShipService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;


@Path("/harbors")
public class HarborController {
    private ShipService shipService;

    private HarborService harborService;

    public HarborController() {
    }

    @Inject
    public void setService(ShipService shipService, HarborService harborService) {
        this.shipService = shipService;
        this.harborService = harborService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getHarbors() {
        return Response
                .ok(GetHarborsResponse.entityToDtoMapper().apply(harborService.findAll()))
                .build();
    }

    @GET
    @Path("/{code}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getHarbor(@PathParam("code") String code) {
        Optional<Harbor> harbor = harborService.find(code.toUpperCase());
        if (harbor.isPresent()) {
            return Response
                    .ok(GetHarborResponse.entityToDtoMapper(harbor.get().getShips()).apply(harbor.get()))
                    .build();
        } else {
            return Response.ok("Harbor with given code not found in database")
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createHarbor(CreateHarborRequest request) {
        Optional<Harbor> harbor = harborService.find(request.getCode().toUpperCase());
        if (harbor.isEmpty()) {
            Harbor new_harbor = CreateHarborRequest
                    .dtoToEntityMapper()
                    .apply(request);
            harborService.create(new_harbor);
            return Response.ok("Harbor with code: " + request.getCode().toUpperCase() + " created successfully")
                    .status(Response.Status.CREATED)
                    .build();


        } else {
            return Response.ok("Harbor with this code already exists")
                    .status(Response.Status.BAD_REQUEST)
                    .build();
        }
    }

    @DELETE
    @Path("/{code}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteHarbor(@PathParam("code") String code) {
        code = code.toUpperCase();
        Optional<Harbor> harbor = harborService.find(code);
        if (harbor.isPresent()) {
            harborService.delete(code);
            return Response.ok("Harbor with code: " + code + " removed successfully")
                    .status(Response.Status.OK)
                    .build();
        } else {
            return Response.ok("Harbor with given code not found in database")
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }
    }

    @PUT
    @Path("/{code}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateHarbor(@PathParam("code") String code, UpdateHarborRequest request) {
        code = code.toUpperCase();
        Optional<Harbor> harbor = harborService.find(code);
        if (harbor.isPresent()) {
            Harbor new_harbor = UpdateHarborRequest
                    .dtoToEntityMapper(code, harbor.get().getShips())
                    .apply(request);
            harborService.update(new_harbor);
            return Response
                    .ok("Harbor with code: " + code + " updated successfully")
                    .build();
        } else {
            return Response.ok("Harbor with given code not found in database")
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }
    }

    @GET
    @Path("/{code}/ships/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getShip(@PathParam("code") String code, @PathParam("id") Long id) {
        code = code.toUpperCase();

        Optional<Harbor> harbor = harborService.find(code);
        if (harbor.isPresent()) {

            Optional<Ship> ship = shipService.findForHarbor(id, code);
            if (ship.isPresent()) {
                return Response
                        .ok(GetShipResponse.entityToDtoMapper().apply(ship.get()))
                        .build();
            } else {
                return Response.ok("Ship with given id not found in database")
                        .status(Response.Status.NOT_FOUND)
                        .build();
            }
        } else {
            return Response.ok("Harbor with given code not found in database")
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }
    }

    @GET
    @Path("/{code}/ships")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getShipForHarbor(@PathParam("code") String code) {
        code = code.toUpperCase();

        Optional<Harbor> harbor = harborService.find(code);
        if (harbor.isPresent()) {

            List<Ship> ships = shipService.findAllForHarbor(code);
            return Response
                    .ok(GetShipsResponse.entityToDtoMapper().apply(ships))
                    .build();

        } else {
            return Response.ok("Harbor with given code not found in database")
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }
    }

    @POST
    @Path("/{code}/ships")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createShip(@PathParam("code") String code, CreateShipRequest request) {

        Optional<Harbor> harbor = harborService.find(code.toUpperCase());
        if (harbor.isPresent()) {
            Ship ship = CreateShipRequest
                    .dtoToEntityMapper(harbor_code -> harborService.find(harbor_code).orElse(null), () -> null)
                    .apply(request);


            shipService.create(ship);

            return Response.ok("Ship with id: " + ship.getId() + " created successfully")
                    .status(Response.Status.CREATED)
                    .build();
        } else {
            return Response.ok("Harbor with given code not found in database")
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }
    }

    @PUT
    @Path("/{code}/ships/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateShip(@PathParam("code") String code, @PathParam("id") Long id, UpdateShipRequest request) {
        code = code.toUpperCase();

        Optional<Harbor> harbor = harborService.find(code);
        if (harbor.isPresent()) {

            Optional<Ship> ship = shipService.findForHarbor(id, code);
            if (ship.isPresent()) {
                Ship new_ship = UpdateShipRequest
                        .dtoToEntityUpdater()
                        .apply(ship.get(), request);
                shipService.update(new_ship);
                return Response
                        .ok("Ship with id: " + id + " updated successfully")
                        .build();
            } else {
                return Response.ok("Ship with given id not found in database")
                        .status(Response.Status.NOT_FOUND)
                        .build();
            }
        } else {
            return Response.ok("Harbor with given code not found in database")
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }
    }

    @DELETE
    @Path("/{code}/ships/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteShip(@PathParam("code") String code, @PathParam("id") Long id) {
        code = code.toUpperCase();

        Optional<Harbor> harbor = harborService.find(code);
        if (harbor.isPresent()) {

            Optional<Ship> ship = shipService.findForHarbor(id, code);
            if (ship.isPresent()) {
                shipService.delete(id);
                return Response
                        .ok("Ship with id: " + id + " removed successfully")
                        .build();
            } else {
                return Response.ok("Ship with given id not found in database")
                        .status(Response.Status.NOT_FOUND)
                        .build();
            }
        } else {
            return Response.ok("Harbor with given code not found in database")
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }
    }


}
