package by.grsu.anikevich.danceClub.web.servlet;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.base.Strings;

import by.grsu.anikevich.danceClub.db.dao.IDao;
import by.grsu.anikevich.danceClub.db.dao.impl.PlaceDaoImpl;
import by.grsu.anikevich.danceClub.db.model.Place;

import by.grsu.anikevich.danceClub.web.dto.TableStateDto;

public class PlaceServlet extends AbstractListServlet {
	private static final IDao<Integer, Place> placeDao = PlaceDaoImpl.INSTANCE;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		System.out.println("doGet");
		String viewParam = req.getParameter("view");
		if ("edit".equals(viewParam)) {
			handleEditView(req, res);
		} else {
			handleListView(req, res);
		}
	}

	private void handleListView(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		int totalRoles = placeDao.count(); // get count of ALL items

		final TableStateDto tableStateDto = resolveTableStateDto(req, totalRoles);
		List<Place> places = placeDao.find(tableStateDto);

		List<Place> dtos = places.stream().map((entity) -> {
			Place dto = new Place();
			dto.setId(entity.getId());
			dto.setName(entity.getName());
			return dto;
		}).collect(Collectors.toList());

		req.setAttribute("list", dtos);
		req.getRequestDispatcher("place-list.jsp").forward(req, res);
	}

	private void handleEditView(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String placeIdStr = req.getParameter("id");
		Place dto = new Place();
		if (!Strings.isNullOrEmpty(placeIdStr)) {
			Integer placeId = Integer.parseInt(placeIdStr);
			Place entity = placeDao.getById(placeId);
			dto.setId(entity.getId());
			dto.setName(entity.getName());
		}
		req.setAttribute("dto", dto);
		req.getRequestDispatcher("place-edit.jsp").forward(req, res);
	}


	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		System.out.println("doPost");
		Place place = new Place();
		String placeIdStr = req.getParameter("id");

		place.setName(req.getParameter("name"));
		if (Strings.isNullOrEmpty(placeIdStr)) {
			placeDao.insert(place);
		} else {
			place.setId(Integer.parseInt(placeIdStr));
			placeDao.update(place);
		}
		res.sendRedirect("/place");
	}

	@Override
	public void doDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		System.out.println("doDelete");
		placeDao.delete(Integer.parseInt(req.getParameter("id")));
	}
}
