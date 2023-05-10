package by.grsu.anikevich.danceClub.web.servlet;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.base.Strings;

import by.grsu.anikevich.danceClub.db.dao.IDao;
import by.grsu.anikevich.danceClub.db.dao.impl.PersoneDaoImpl;
import by.grsu.anikevich.danceClub.db.dao.impl.RoleDaoImpl;
import by.grsu.anikevich.danceClub.db.model.Persone;
import by.grsu.anikevich.danceClub.db.model.Role;
import by.grsu.anikevich.danceClub.web.dto.PersoneDto;
import by.grsu.anikevich.danceClub.web.dto.TableStateDto;

public class PersoneServlet extends AbstractListServlet {
	private static final IDao<Integer, Persone> personeDao = PersoneDaoImpl.INSTANCE;
	private static final IDao<Integer, Role> roleDao = RoleDaoImpl.INSTANCE;

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
		int totalPersones = personeDao.count(); // get count of ALL items

		final TableStateDto tableStateDto = resolveTableStateDto(req, totalPersones);
		List<Persone> persones = personeDao.find(tableStateDto);

		List<PersoneDto> dtos = persones.stream().map((entity) -> {
			PersoneDto dto = new PersoneDto();
			dto.setId(entity.getId());
			dto.setFirstName(entity.getFirstName());
			dto.setSecondName(entity.getSecondName());
			dto.setPatronymic(entity.getPatronymic());
			dto.setMail(entity.getMail());

			Role role = roleDao.getById(entity.getRoleId());
			dto.setRoleName(role.getName());
			//dto.setRoleId(entity.getRoleId());
			return dto;
		}).collect(Collectors.toList());

		req.setAttribute("list", dtos);
		req.getRequestDispatcher("persone-list.jsp").forward(req, res);
	}

	private void handleEditView(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String personeIdStr = req.getParameter("id");
		PersoneDto dto = new PersoneDto();
		if (!Strings.isNullOrEmpty(personeIdStr)) {
			Integer personeId = Integer.parseInt(personeIdStr);
			Persone entity = personeDao.getById(personeId);
			dto.setId(entity.getId());
			dto.setFirstName(entity.getFirstName());
			dto.setSecondName(entity.getSecondName());
			dto.setPatronymic(entity.getPatronymic());
			dto.setMail(entity.getMail());
			dto.setRoleId(entity.getRoleId());
		}
		req.setAttribute("dto", dto);
		req.setAttribute("allRoles", getAllRolesDtos());
		req.getRequestDispatcher("persone-edit.jsp").forward(req, res);
	}
	private List<Role> getAllRolesDtos() {
		return roleDao.getAll().stream().map((entity) -> {
			Role dto = new Role();
			dto.setId(entity.getId());
			dto.setName(entity.getName());
			return dto;
		}).collect(Collectors.toList());
	}


	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		System.out.println("doPost");
		Persone persone = new Persone();
		String personeIdStr = req.getParameter("id");
		String roleIdStr = req.getParameter("roleId");
		persone.setFirstName(req.getParameter("firstName"));
		persone.setSecondName(req.getParameter("secondName"));
		persone.setPatronymic(req.getParameter("patronymic"));
		persone.setMail(req.getParameter("mail"));
		persone.setRoleId(roleIdStr == null ? null : Integer.parseInt(roleIdStr));
		if (Strings.isNullOrEmpty(personeIdStr)) {
			personeDao.insert(persone);
		} else {
			persone.setId(Integer.parseInt(personeIdStr));
			personeDao.update(persone);
		}
		res.sendRedirect("/persone");
	}

	@Override
	public void doDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		System.out.println("doDelete");
		personeDao.delete(Integer.parseInt(req.getParameter("id")));
	}
}
