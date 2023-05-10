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
import by.grsu.anikevich.danceClub.db.dao.impl.PlaceDaoImpl;
import by.grsu.anikevich.danceClub.db.dao.impl.TrainingDaoImpl;
import by.grsu.anikevich.danceClub.db.model.Groupe;
import by.grsu.anikevich.danceClub.db.model.Persone;
import by.grsu.anikevich.danceClub.db.model.Place;
import by.grsu.anikevich.danceClub.db.model.Training;
import by.grsu.anikevich.danceClub.web.dto.TableStateDto;
import by.grsu.anikevich.danceClub.web.dto.TrainingDto;


public class TrainingServlet extends AbstractListServlet {
	private static final IDao<Integer, Training> trainingDao = TrainingDaoImpl.INSTANCE;
	private static final IDao<Integer, Persone> personeDao = PersoneDaoImpl.INSTANCE;
	private static final IDao<Integer, Place> placeDao = PlaceDaoImpl.INSTANCE;
	private static final IDao<Integer, Groupe> groupeDao = GroupeDaoImpl.INSTANCE;
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
		int totalTrainings = trainingDao.count(); // get count of ALL items
		
		String parameter = req.getParameter("groupeId");

		final TableStateDto tableStateDto = resolveTableStateDto(req, totalTrainings);
		
		if (!Strings.isNullOrEmpty(parameter) ) {

			List<Groupe> groups = groupeDao.getAllwithselfId(Integer.parseInt(parameter));
			List<Training> trainings= new ArrayList<>();
			for(Integer i=0;i<groups.size();i++)
			{
				List<Training> tmp= new ArrayList<>();
				tmp= trainingDao.getAllwithId(groups.get(i).getId());
				for(Integer j=0;j<tmp.size();j++)
				{
					trainings.add(tmp.get(j));
				}
			}
			List<TrainingDto> dtos = trainings.stream().map((entity) -> {
				TrainingDto dto = new TrainingDto();
				dto.setId(entity.getId());
				dto.setData(entity.getData());
				

				Persone persone = personeDao.getById(entity.getCoachId());
				dto.setCoachName(persone.getFirstName()+" "+ persone.getSecondName());
				
				Groupe groupe = groupeDao.getById(entity.getGroupeId());
				dto.setGroupeName(groupe.getName());
				
				Place place = placeDao.getById(entity.getPlaceId());
				dto.setPlaceName(place.getName());
				//dto.setRoleId(entity.getRoleId());
				return dto;
			}).collect(Collectors.toList());

			req.setAttribute("list", dtos);
			req.getRequestDispatcher("training-list.jsp").forward(req, res);
		}else {
		
		
		
		
		List<Training> trainings = trainingDao.find(tableStateDto);

		List<TrainingDto> dtos = trainings.stream().map((entity) -> {
			TrainingDto dto = new TrainingDto();
			dto.setId(entity.getId());
			dto.setData(entity.getData());
			

			Persone persone = personeDao.getById(entity.getCoachId());
			dto.setCoachName(persone.getFirstName()+" "+ persone.getSecondName());
			
			Groupe groupe = groupeDao.getById(entity.getGroupeId());
			dto.setGroupeName(groupe.getName());
			
			Place place = placeDao.getById(entity.getPlaceId());
			dto.setPlaceName(place.getName());
			//dto.setRoleId(entity.getRoleId());
			return dto;
		}).collect(Collectors.toList());

		req.setAttribute("list", dtos);
		req.getRequestDispatcher("training-list.jsp").forward(req, res);
		
		
		}
	}

	private void handleEditView(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String trainingIdStr = req.getParameter("id");
		TrainingDto dto = new TrainingDto();
		if (!Strings.isNullOrEmpty(trainingIdStr)) {
			Integer trainingId = Integer.parseInt(trainingIdStr);
			Training entity = trainingDao.getById(trainingId);
			dto.setId(entity.getId());
			dto.setPlaceId(entity.getPlaceId());
			dto.setCoachId(entity.getCoachId());
			dto.setGroupeId(entity.getGroupeId());
			dto.setData(entity.getData());
			
		}
		req.setAttribute("dto", dto);
		req.setAttribute("allCoachs", getAllPersoneDtos());
		req.setAttribute("allGroups", getAllGroupsDtos());
		req.setAttribute("allPlaces", getAllPlaycesDtos());
		req.getRequestDispatcher("training-edit.jsp").forward(req, res);
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
	private List<Place> getAllPlaycesDtos() {
		return placeDao.getAll().stream().map((entity) -> {
			Place dto = new Place();
			dto.setId(entity.getId());
			dto.setName(entity.getName());
			return dto;
		}).collect(Collectors.toList());
	}
	private List<Groupe> getAllGroupsDtos() {
		return groupeDao.getAll().stream().map((entity) -> {
			Groupe dto = new Groupe();
			dto.setId(entity.getId());
			dto.setName(entity.getName());
			return dto;
		}).collect(Collectors.toList());
	}



	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		System.out.println("doPost");
		Training training = new Training();
		String trainingIdStr = req.getParameter("id");
		String placeIdStr = req.getParameter("placeId");
		String coachIdStr = req.getParameter("coachId");
		String groupeIdStr = req.getParameter("groupeId");
		
		
		training.setDate(java.sql.Date.valueOf(req.getParameter("data")));
		
		
		training.setPlaceId(placeIdStr == null ? null : Integer.parseInt(placeIdStr));
		training.setCoachId(coachIdStr == null ? null : Integer.parseInt(coachIdStr));
		training.setGroupeId(groupeIdStr == null ? null : Integer.parseInt(groupeIdStr));
		
		
		if (Strings.isNullOrEmpty(trainingIdStr)) {
			trainingDao.insert(training);
		} else {
			training.setId(Integer.parseInt(trainingIdStr));
			trainingDao.update(training);
		}
		res.sendRedirect("/training");
	}

	@Override
	public void doDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		System.out.println("doDelete");
		trainingDao.delete(Integer.parseInt(req.getParameter("id")));
	}
}

