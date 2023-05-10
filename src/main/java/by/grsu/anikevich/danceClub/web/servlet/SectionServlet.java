package by.grsu.anikevich.danceClub.web.servlet;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.base.Strings;

import by.grsu.anikevich.danceClub.db.dao.IDao;
import by.grsu.anikevich.danceClub.db.dao.impl.SectionDaoImpl;
import by.grsu.anikevich.danceClub.db.model.Section;
import by.grsu.anikevich.danceClub.web.dto.TableStateDto;

public class SectionServlet extends AbstractListServlet {
	private static final IDao<Integer, Section> sectionDao = SectionDaoImpl.INSTANCE;

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
		int totalSections = sectionDao.count(); // get count of ALL items

		final TableStateDto tableStateDto = resolveTableStateDto(req, totalSections);
		List<Section> sections = sectionDao.find(tableStateDto);

		List<Section> dtos = sections.stream().map((entity) -> {
			Section dto = new Section();
			dto.setId(entity.getId());
			dto.setName(entity.getName());
			return dto;
		}).collect(Collectors.toList());

		req.setAttribute("list", dtos);
		req.getRequestDispatcher("section-list.jsp").forward(req, res);
	}

	private void handleEditView(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String sectionIdStr = req.getParameter("id");
		Section dto = new Section();
		if (!Strings.isNullOrEmpty(sectionIdStr)) {
			Integer sectionId = Integer.parseInt(sectionIdStr);
			Section entity = sectionDao.getById(sectionId);
			dto.setId(entity.getId());
			dto.setName(entity.getName());
		}
		req.setAttribute("dto", dto);
		req.getRequestDispatcher("section-edit.jsp").forward(req, res);
	}


	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		System.out.println("doPost");
		Section section = new Section();
		String sectionIdStr = req.getParameter("id");

		section.setName(req.getParameter("name"));
		if (Strings.isNullOrEmpty(sectionIdStr)) {
			sectionDao.insert(section);
		} else {
			section.setId(Integer.parseInt(sectionIdStr));
			sectionDao.update(section);
		}
		res.sendRedirect("/section");
	}

	@Override
	public void doDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		System.out.println("doDelete");
		sectionDao.delete(Integer.parseInt(req.getParameter("id")));
	}
}

