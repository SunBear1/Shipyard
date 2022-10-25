package org.example.Harbor.HarborServlet;

import org.example.Harbor.DTO.GetHarborResponse;
import org.example.Harbor.DTO.GetHarborsResponse;
import org.example.Harbor.Harbor;
import org.example.Harbor.Service.HarborService;
import org.example.Servlet.ServletUtility;
import org.example.Ship.Service.ShipService;

import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet(urlPatterns = {
        HarborServlet.Paths.HARBOR + "/*",
        HarborServlet.Paths.HARBORS,
})
public class HarborServlet extends HttpServlet {
    private final HarborService harborService;
    private final ShipService shipService;

    @Inject
    public HarborServlet(HarborService harborService, ShipService shipService) {
        this.harborService = harborService;
        this.shipService = shipService;
    }

    public static class Paths {

        public static final String HARBOR = "/api/harbor";
        public static final String HARBORS = "/api/harbors";

    }

    private final Jsonb jsonb = JsonbBuilder.create();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String servletPath = request.getServletPath();

        if (Paths.HARBOR.equals(servletPath)) {
            getHarbor(request, response);
        } else if (Paths.HARBORS.equals(servletPath)) {
            getHarbors(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }

    }

    private void getHarbor(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String code = ServletUtility.parseRequestPath(request).replaceAll("/", "");
        Optional<Harbor> harbor = harborService.find(code);
        if (harbor.isPresent()) {
            harbor.get().setShips(shipService.findAllForHarbor(code));
            response.setContentType("application/json");
            response.getWriter()
                    .write(jsonb.toJson(GetHarborResponse.entityToDtoMapper().apply(harbor.get())));
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.getWriter()
                    .write("Harbor not found");
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void getHarbors(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.getWriter().write(jsonb.toJson(GetHarborsResponse.entityToDtoMapper().apply(harborService.findAll())));
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String path = ServletUtility.parseRequestPath(request);
        if (Paths.HARBOR.equals(request.getServletPath())) {
            deleteHarbor(request, response);
            return;
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    private void deleteHarbor(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //Parsed request path is valid with character pattern and can contain starting and ending '/'.
        String code = ServletUtility.parseRequestPath(request).replaceAll("/", "");
        Optional<Harbor> harbor = harborService.find(code);

        if (harbor.isPresent()) {
            harborService.delete(harbor.get().getCode());
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

}
