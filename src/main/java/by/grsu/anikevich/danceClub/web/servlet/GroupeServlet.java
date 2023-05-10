package by.grsu.anikevich.danceClub.web.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.base.Strings;


import by.grsu.anikevich.danceClub.db.dao.IDao;
import by.grsu.anikevich.danceClub.db.dao.impl.GroupeDaoImpl;
import by.grsu.anikevich.danceClub.db.dao.impl.PersoneDaoImpl;
import by.grsu.anikevich.danceClub.db.dao.impl.SectionDaoImpl;
import by.grsu.anikevich.danceClub.db.dao.impl.StateDaoImpl;
import by.grsu.anikevich.danceClub.db.model.Groupe;
import by.grsu.anikevich.danceClub.db.model.Persone;
import by.grsu.anikevich.danceClub.db.model.Section;
import by.grsu.anikevich.danceClub.db.model.State;
import by.grsu.anikevich.danceClub.web.dto.GroupeDto;
import by.grsu.anikevich.danceClub.web.dto.TableStateDto;

public class GroupeServlet extends AbstractListServlet {
	private static final IDao<Integer, Groupe> groupeDao = GroupeDaoImpl.INSTANCE;
	private static final IDao<Integer, Persone> personeDao = PersoneDaoImpl.INSTANCE;
	private static final IDao<Integer, Section> sectionDao = SectionDaoImpl.INSTANCE;
	private static final IDao<Integer, State> stateDao = StateDaoImpl.INSTANCE;
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
		int totalGroupes = groupeDao.count(); // get count of ALL items
		
		String parameter = req.getParameter("personId");

		final TableStateDto tableStateDto = resolveTableStateDto(req, totalGroupes);
		
		if (!Strings.isNullOrEmpty(parameter) ) {
			List<Persone> persons = personeDao.getAllwithId(Integer.parseInt(parameter));
			List<Groupe> groupes= new ArrayList<>();
			for(Integer i=0;i<persons.size();i++)
			{
				List<Groupe> tmp= new ArrayList<>();
				tmp= groupeDao.getAllwithId(persons.get(i).getId());
				for(Integer j=0;j<tmp.size();j++)
				{
					groupes.add(tmp.get(j));
				}
			}
			List<GroupeDto> dtos = groupes.stream().map((entity) -> {
				GroupeDto dto = new GroupeDto();
				dto.setId(entity.getId());
				dto.setName(entity.getName());
				dto.setData(entity.getData());
				

				Persone persone = personeDao.getById(entity.getPersonId());
				dto.setPersoneName(persone.getFirstName()+" "+ persone.getSecondName());
				
				Section section = sectionDao.getById(entity.getSectionId());
				dto.setSectionName(section.getName());
				
				State state = stateDao.getById(entity.getStateId());
				dto.setStateName(state.getName());
				//dto.setRoleId(entity.getRoleId());
				return dto;
			}).collect(Collectors.toList());

			req.setAttribute("list", dtos);
			req.getRequestDispatcher("groupe-list.jsp").forward(req, res);
			
			
			
		}
		else
		{
			List<Groupe> groupes = groupeDao.find(tableStateDto);
		List<GroupeDto> dtos = groupes.stream().map((entity) -> {
			GroupeDto dto = new GroupeDto();
			dto.setId(entity.getId());
			dto.setName(entity.getName());
			dto.setData(entity.getData());
			

			Persone persone = personeDao.getById(entity.getPersonId());
			dto.setPersoneName(persone.getFirstName()+" "+ persone.getSecondName());
			
			Section section = sectionDao.getById(entity.getSectionId());
			dto.setSectionName(section.getName());
			
			State state = stateDao.getById(entity.getStateId());
			dto.setStateName(state.getName());
			//dto.setRoleId(entity.getRoleId());
			return dto;
		}).collect(Collectors.toList());

		req.setAttribute("list", dtos);
		req.getRequestDispatcher("groupe-list.jsp").forward(req, res);
		}
	}

	private void handleEditView(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String groupeIdStr = req.getParameter("id");
		GroupeDto dto = new GroupeDto();
		if (!Strings.isNullOrEmpty(groupeIdStr)) {
			Integer groupeId = Integer.parseInt(groupeIdStr);
			Groupe entity = groupeDao.getById(groupeId);
			dto.setId(entity.getId());
			dto.setName(entity.getName());
			dto.setPersonId(entity.getPersonId());
			dto.setSectionId(entity.getSectionId());
			dto.setStateId(entity.getStateId());
			dto.setData(entity.getData());
			
		}
		req.setAttribute("dto", dto);
		req.setAttribute("allPersons", getAllPersoneDtos());
		req.setAttribute("allSections", getAllSectionsDtos());
		req.setAttribute("allStates", getAllStatesDtos());
		req.getRequestDispatcher("groupe-edit.jsp").forward(req, res);
	}
	private List<Persone> getAllPersoneDtos() {
		return personeDao.getAll().stream().map((entity) -> {
			Persone dto = new Persone();
			dto.setId(entity.getId());
			dto.setFirstName(entity.getFirstName());
			dto.setSecondName(entity.getSecondName());
			return dto;
		}).collect(Collectors.toList());
	}
	private List<Section> getAllSectionsDtos() {
		return sectionDao.getAll().stream().map((entity) -> {
			Section dto = new Section();
			dto.setId(entity.getId());
			dto.setName(entity.getName());
			return dto;
		}).collect(Collectors.toList());
	}
	private List<State> getAllStatesDtos() {
		return stateDao.getAll().stream().map((entity) -> {
			State dto = new State();
			dto.setId(entity.getId());
			dto.setName(entity.getName());

			return dto;
		}).collect(Collectors.toList());
	}



	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		System.out.println("doPost");
		Groupe groupe = new Groupe();
		String groupeIdStr = req.getParameter("id");
		String personeIdStr = req.getParameter("personId");
		String sectionIdStr = req.getParameter("sectionId");
		String stateIdStr = req.getParameter("stateId");
		
		groupe.setName(req.getParameter("name"));
		groupe.setData(java.sql.Date.valueOf(req.getParameter("data")));
		
		
		groupe.setPersonId(personeIdStr == null ? null : Integer.parseInt(personeIdStr));
		groupe.setSectionId(sectionIdStr == null ? null : Integer.parseInt(sectionIdStr));
		groupe.setStateId(stateIdStr == null ? null : Integer.parseInt(stateIdStr));
		
		
		if (Strings.isNullOrEmpty(groupeIdStr)) {
			groupeDao.insert(groupe);
		} else {
			groupe.setId(Integer.parseInt(groupeIdStr));
			groupeDao.update(groupe); 
		}
		res.sendRedirect("/groupe");
	}

	@Override
	public void doDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		System.out.println("doDelete");
		groupeDao.delete(Integer.parseInt(req.getParameter("id")));
	}
}
