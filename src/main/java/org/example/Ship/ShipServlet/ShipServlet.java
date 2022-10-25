package org.example.Ship.ShipServlet;

import org.example.Harbor.Service.HarborService;
import org.example.Servlet.ServletUtility;
import org.example.Servlet.UrlFactory;
import org.example.Ship.DTO.CreateShipRequest;
import org.example.Ship.DTO.GetShipResponse;
import org.example.Ship.DTO.GetShipsResponse;
import org.example.Ship.DTO.UpdateShipRequest;
import org.example.Ship.Service.ShipService;
import org.example.Ship.Ship;

import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.HttpHeaders;
import java.io.IOException;
import java.util.Optional;

@WebServlet(urlPatterns = {
        ShipServlet.Paths.SHIP + "/*",
        ShipServlet.Paths.HARBOR_SHIPS,
})
public class ShipServlet extends HttpServlet {

    private final ShipService shipService;
    private final HarborService harborService;

    @Inject
    public ShipServlet(ShipService shipService, HarborService harborService) {
        this.shipService = shipService;
        this.harborService = harborService;
    }

    public static class Paths {

        public static final String SHIP = "/api/ship";
        public static final String HARBOR_SHIPS = "/api/ships";

    }

    public static class Patterns {

        /**
         * All characters.
         */
        public static final String SHIPS = "^/?$";

        /**
         * Specified character.
         */
        public static final String SHIP = "^/[0-9]+/?$";

    }


    private final Jsonb jsonb = JsonbBuilder.create();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String servletPath = request.getServletPath();

        if (Paths.SHIP.equals(servletPath)) {
            getShip(request, response);
        } else if (Paths.HARBOR_SHIPS.equals(servletPath)) {
            getShips(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }

    }

    private void getShip(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // ShipService service = (ShipService) request.getServletContext().getAttribute("shipService");
        Long id = Long.parseLong(ServletUtility.parseRequestPath(request).replaceAll("/", ""));
        Optional<Ship> ship = shipService.find(id);
        if (ship.isPresent()) {
            response.setContentType("application/json");
            response.getWriter()
                    .write(jsonb.toJson(GetShipResponse.entityToDtoMapper().apply(ship.get())));
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.getWriter()
                    .write("Ship not found");
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void getShips(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //ShipService service = (ShipService) request.getServletContext().getAttribute("shipService");
        response.setContentType("application/json");
        response.getWriter().write(jsonb.toJson(GetShipsResponse.entityToDtoMapper().apply(shipService.findAll())));
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (Paths.SHIP.equals(request.getServletPath())) {
            deleteHarborShip(request, response);
            return;
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    private void deleteHarborShip(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //Parsed request path is valid with character pattern and can contain starting and ending '/'.
        Long id = Long.parseLong(ServletUtility.parseRequestPath(request).replaceAll("/", ""));
        Optional<Ship> ship = shipService.find(id);

        if (ship.isPresent()) {
            shipService.delete(ship.get().getId());
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String path = ServletUtility.parseRequestPath(request);
        if (Paths.HARBOR_SHIPS.equals(request.getServletPath())) {
            postHarborShip(request, response);
            return;
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String path = ServletUtility.parseRequestPath(request);
        if (Paths.SHIP.equals(request.getServletPath())) {
            if (path.matches(Patterns.SHIP)) {
                putHarborShip(request, response);
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    private void putHarborShip(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //Parsed request path is valid with character pattern and can contain starting and ending '/'.
        Long id = Long.parseLong(ServletUtility.parseRequestPath(request).replaceAll("/", ""));
        Optional<Ship> ship = shipService.find(id);

        if (ship.isPresent()) {
            UpdateShipRequest requestBody = jsonb.fromJson(request.getInputStream(),
                    UpdateShipRequest.class);

            UpdateShipRequest.dtoToEntityUpdater().apply(ship.get(), requestBody);

            shipService.update(ship.get());
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

    }

    private void postHarborShip(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CreateShipRequest requestBody = jsonb.fromJson(request.getInputStream(), CreateShipRequest.class);

        Ship ship = CreateShipRequest
                .dtoToEntityMapper(harbor_code -> harborService.find(harbor_code).orElse(null), () -> null)
                .apply(requestBody);

        try {
            shipService.create(ship);
            //When creating new resource, its location should be returned.
            response.addHeader(HttpHeaders.LOCATION,
                    UrlFactory.createUrl(request, Paths.HARBOR_SHIPS, ship.getId().toString()));
            //When creating new resource, appropriate code should be set.
            response.setStatus(HttpServletResponse.SC_CREATED);
        } catch (IllegalArgumentException ex) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }


}
