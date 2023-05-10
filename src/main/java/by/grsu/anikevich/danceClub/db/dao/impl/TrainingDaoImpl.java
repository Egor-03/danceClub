package by.grsu.anikevich.danceClub.db.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import by.grsu.anikevich.danceClub.db.dao.AbstractDao;
import by.grsu.anikevich.danceClub.db.dao.IDao;
import by.grsu.anikevich.danceClub.db.model.Groupe;
import by.grsu.anikevich.danceClub.db.model.Training;
import by.grsu.anikevich.danceClub.web.dto.SortDto;
import by.grsu.anikevich.danceClub.web.dto.TableStateDto;

public class TrainingDaoImpl extends AbstractDao implements IDao<Integer, Training>{
	public static final TrainingDaoImpl INSTANCE = new TrainingDaoImpl();

	private TrainingDaoImpl() {
		super();
	}

	@Override
	public void insert(Training entity) {
		try (Connection c = createConnection()) {
			PreparedStatement pstmt = c.prepareStatement(
					"insert into training(place_id,coach_id,groupe_id,data) values(?,?,?,?)");
			pstmt.setInt(1, entity.getPlaceId());
			pstmt.setInt(2, entity.getCoachId());
			pstmt.setInt(3, entity.getGroupeId());
			pstmt.setDate(4, entity.getData());
			pstmt.executeUpdate();
			entity.setId(getGeneratedId(c, "training"));
		} catch (SQLException e) {
			throw new RuntimeException("can't insert Training entity", e);
		}

	}

	@Override
	public void update(Training entity) {
		try (Connection c = createConnection()) {
			PreparedStatement pstmt = c.prepareStatement(
					"update training set  data=?  where id =? ");
			//pstmt.setInt(1, entity.getRoleId());
			pstmt.setDate(1, entity.getData());
			pstmt.setInt(2, entity.getId());
			pstmt.executeUpdate();
			entity.setId(getGeneratedId(c, "training"));
		} catch (SQLException e) {
			throw new RuntimeException("can't update Training entity", e);
		}
	}

	@Override
	public void delete(Integer id) {
		try (Connection c = createConnection()) {
			PreparedStatement pstmt = c.prepareStatement("delete from training where id=?");
			pstmt.setInt(1, id);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException("can't delete Training entity", e);
		}
	}

	@Override
	public Training getById(Integer id) {
		Training entity = null;
		try (Connection c = createConnection()) {
			PreparedStatement pstmt = c.prepareStatement("select * from training where id=?");
			pstmt.setInt(1, id);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				entity = rowToEntity(rs);
			}
		} catch (SQLException e) {
			throw new RuntimeException("can't get Training entity by id", e);
		}

		return entity;
	}

	@Override
	public List<Training> getAll() {
		List<Training> entitiesList = new ArrayList<>();
		try (Connection c = createConnection()) {
			ResultSet rs = c.createStatement().executeQuery("select * from training");
			while (rs.next()) {
				Training entity = rowToEntity(rs);
				entitiesList.add(entity);
			}
		} catch (SQLException e) {
			throw new RuntimeException("can't select Training entities", e);
		}

		return entitiesList;
	}

	private Training rowToEntity(ResultSet rs) throws SQLException {
		Training entity = new Training();
		entity.setId(rs.getInt("id"));
		entity.setPlaceId(rs.getInt("place_id"));
		entity.setCoachId(rs.getInt("coach_id"));
		entity.setGroupeId(rs.getInt("groupe_id"));
		entity.setDate(rs.getDate("data"));
		
		return entity;
	}

	@Override
	public List<Training> getAllwithId(Integer id) {
		List<Training> entitiesList = new ArrayList<>();
		try (Connection c = createConnection()) {
			PreparedStatement pstmt = c.prepareStatement("select * from training where groupe_id=?");
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Training entity = rowToEntity(rs);
				entitiesList.add(entity);
			}
		} catch (SQLException e) {
			throw new RuntimeException("can't select Training entities", e);
		}

		return entitiesList;
	}

	@Override
	public List<Training> find(TableStateDto tableStateDto) {
		List<Training> entitiesList = new ArrayList<>();
		try (Connection c = createConnection()) {
			StringBuilder sql = new StringBuilder("select * from training");

			final SortDto sortDto = tableStateDto.getSort();
			if (sortDto != null) {
				sql.append(String.format(" order by %s %s", sortDto.getColumn(), resolveSortOrder(sortDto)));
			}

			sql.append(" limit " + tableStateDto.getItemsPerPage());
			sql.append(" offset " + resolveOffset(tableStateDto));

			System.out.println("searching Requests using SQL: " + sql);
			ResultSet rs = c.createStatement().executeQuery(sql.toString());
			while (rs.next()) {
				Training entity = rowToEntity(rs);
				entitiesList.add(entity);
			}
		} catch (SQLException e) {
			throw new RuntimeException("can't select Training entities", e);
		}
		return entitiesList;
	}

	@Override
	public int count() {
		try (Connection c = createConnection()) {
			PreparedStatement pstmt = c.prepareStatement("select count(*) as c from training");
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			return rs.getInt("c");
		} catch (SQLException e) {
			throw new RuntimeException("can't get Training count", e);
		}
	}

	@Override
	public List<Groupe> getAllwithselfId(Integer parseInt) {
		// TODO Auto-generated method stub
		return null;
	}


}
