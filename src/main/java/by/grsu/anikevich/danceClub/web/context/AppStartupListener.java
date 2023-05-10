package by.grsu.anikevich.danceClub.web.context;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Date;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import by.grsu.anikevich.danceClub.db.dao.AbstractDao;
import by.grsu.anikevich.danceClub.db.dao.IDao;
import by.grsu.anikevich.danceClub.db.dao.impl.GroupeDaoImpl;
import by.grsu.anikevich.danceClub.db.dao.impl.PersoneDaoImpl;
import by.grsu.anikevich.danceClub.db.dao.impl.PlaceDaoImpl;
import by.grsu.anikevich.danceClub.db.dao.impl.RoleDaoImpl;
import by.grsu.anikevich.danceClub.db.dao.impl.SectionDaoImpl;
import by.grsu.anikevich.danceClub.db.dao.impl.StateDaoImpl;
import by.grsu.anikevich.danceClub.db.dao.impl.TrainingDaoImpl;
import by.grsu.anikevich.danceClub.db.model.Groupe;
import by.grsu.anikevich.danceClub.db.model.Persone;
import by.grsu.anikevich.danceClub.db.model.Place;
import by.grsu.anikevich.danceClub.db.model.Role;
import by.grsu.anikevich.danceClub.db.model.Section;
import by.grsu.anikevich.danceClub.db.model.State;
import by.grsu.anikevich.danceClub.db.model.Training;


public class AppStartupListener implements ServletContextListener {
	private static final IDao<Integer, Place> placeDao = PlaceDaoImpl.INSTANCE;
	private static final IDao<Integer, Persone> personeDao = PersoneDaoImpl.INSTANCE;
	private static final IDao<Integer, Role> roleDao = RoleDaoImpl.INSTANCE;
	private static final IDao<Integer, Section> sectionDao = SectionDaoImpl.INSTANCE;
	private static final IDao<Integer, State> stateDao = StateDaoImpl.INSTANCE;
	private static final IDao<Integer, Groupe> groupeDao = GroupeDaoImpl.INSTANCE;
	private static final IDao<Integer, Training> trainingDao = TrainingDaoImpl.INSTANCE;

	private static final String DB_NAME = "production-db";

	private void initDb() throws SQLException {
		AbstractDao.init(DB_NAME);
		if (!AbstractDao.isDbExist()) {
			System.out.println(String.format("DB '%s' doesn't exist and will be created", DB_NAME));
			AbstractDao.createDbSchema();
			loadInitialData();
		} else {
			System.out.println(String.format("DB '%s' exists", DB_NAME));
		}
	}

	private void loadInitialData() {
		Role roleEntity = new Role();
		roleEntity.setName("rolename");
		roleDao.insert(roleEntity);
		System.out.println("created: " + roleEntity);

		State stateEntity = new State();
		stateEntity.setName("statename");
		stateDao.insert(stateEntity);
		System.out.println("created: " + stateEntity);

		Place placeEntity = new Place();
		placeEntity.setName("placename");
		placeDao.insert(placeEntity);
		System.out.println("created: " + placeEntity);

		Section sectionEntity = new Section();
		sectionEntity.setName("sectionname");
		sectionDao.insert(sectionEntity);
		System.out.println("created: " + sectionEntity);


		Persone personeEntity = new Persone();
		personeEntity.setRoleId(roleEntity.getId());
		personeEntity.setFirstName("first_name");
		personeEntity.setSecondName("second_name");
		personeEntity.setPatronymic("patronymic");
		personeEntity.setMail("mail");
		personeDao.insert(personeEntity);
		System.out.println("created: " + personeEntity);

		Groupe groupeEntity = new Groupe();
		Date d = new Date(1231431); 
		

		groupeEntity.setName("groupeName");
		groupeEntity.setPersonId(personeEntity.getId());
		groupeEntity.setSectionId(sectionEntity.getId());
		groupeEntity.setData(d);
		groupeEntity.setStateId(stateEntity.getId());
		groupeDao.insert(groupeEntity);
		System.out.println("created: " + groupeEntity);



		Training trainingEntity = new Training();
		trainingEntity.setPlaceId(placeEntity.getId());
		trainingEntity.setCoachId(personeEntity.getId());
		trainingEntity.setGroupeId(groupeEntity.getId());
		trainingDao.insert(trainingEntity);
		System.out.println("created: " + trainingEntity);
		System.out.println("initial data created");
	}

	private Timestamp getCurrentTime() {
		return new Timestamp(new java.util.Date().getTime());
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("contextInitialized");
		try {
			initDb();
		} catch (SQLException e) {
			throw new RuntimeException("can't init DB", e);
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("contextDestroyed");
	}
}
