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
import by.grsu.anikevich.danceClub.db.model.Place;
import by.grsu.anikevich.danceClub.web.dto.SortDto;
import by.grsu.anikevich.danceClub.web.dto.TableStateDto;

public class PlaceDaoImpl extends AbstractDao implements IDao<Integer, Place>{
	public static final PlaceDaoImpl INSTANCE = new PlaceDaoImpl();

	private PlaceDaoImpl() {
		super();
	}

	@Override
	public void insert(Place entity) {
		try (Connection c = createConnection()) {
			PreparedStatement pstmt = c.prepareStatement("insert into place(name) values(?)");
			pstmt.setString(1, entity.getName());
			pstmt.executeUpdate();
			entity.setId(getGeneratedId(c, "place"));
		} catch (SQLException e) {
			throw new RuntimeException("can't insert Place entity", e);
		}

	}

	@Override
	public void update(Place entity) {
		try (Connection c = createConnection()) {
			PreparedStatement pstmt = c.prepareStatement("update place set name=? where id =?");
			pstmt.setString(1, entity.getName());
			pstmt.setInt(2, entity.getId());
			pstmt.executeUpdate();
			entity.setId(getGeneratedId(c, "place"));
		} catch (SQLException e) {
			throw new RuntimeException("can't update Place entity", e);
		}
	}

	@Override
	public void delete(Integer id) {
		try (Connection c = createConnection()) {
			PreparedStatement pstmt = c.prepareStatement("delete from place where id=?");
			pstmt.setInt(1, id);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException("can't delete Place entity", e);
		}
	}

	@Override
	public Place getById(Integer id) {
		Place entity = null;
		try (Connection c = createConnection()) {
			PreparedStatement pstmt = c.prepareStatement("select * from place where id=?");
			pstmt.setInt(1, id);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				entity = rowToEntity(rs);
			}
		} catch (SQLException e) {
			throw new RuntimeException("can't get Place entity by id", e);
		}

		return entity;
	}

	@Override
	public List<Place> getAll() {
		List<Place> entitiesList = new ArrayList<>();
		try (Connection c = createConnection()) {
			ResultSet rs = c.createStatement().executeQuery("select * from place");
			while (rs.next()) {
				Place entity = rowToEntity(rs);
				entitiesList.add(entity);
			}
		} catch (SQLException e) {
			throw new RuntimeException("can't select Place entities", e);
		}

		return entitiesList;
	}

	private Place rowToEntity(ResultSet rs) throws SQLException {
		Place entity = new Place();
		entity.setId(rs.getInt("id"));
		entity.setName(rs.getString("name"));
		return entity;
	}

	@Override
	public List<Place> getAllwithId(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override

	public List<Place> find(TableStateDto tableStateDto) {
		List<Place> entitiesList = new ArrayList<>();
		try (Connection c = createConnection()) {
			StringBuilder sql = new StringBuilder("select * from place");

			final SortDto sortDto = tableStateDto.getSort();
			if (sortDto != null) {
				sql.append(String.format(" order by %s %s", sortDto.getColumn(), resolveSortOrder(sortDto)));
			}

			sql.append(" limit " + tableStateDto.getItemsPerPage());
			sql.append(" offset " + resolveOffset(tableStateDto));

			System.out.println("searching Requests using SQL: " + sql);
			ResultSet rs = c.createStatement().executeQuery(sql.toString());
			while (rs.next()) {
				Place entity = rowToEntity(rs);
				entitiesList.add(entity);
			}
		} catch (SQLException e) {
			throw new RuntimeException("can't select Place entities", e);
		}
		return entitiesList;
	}

	@Override
	public int count() {
		try (Connection c = createConnection()) {
			PreparedStatement pstmt = c.prepareStatement("select count(*) as c from place");
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			return rs.getInt("c");
		} catch (SQLException e) {
			throw new RuntimeException("can't get Place count", e);
		}
	}

	@Override
	public List<Groupe> getAllwithselfId(Integer parseInt) {
		// TODO Auto-generated method stub
		return null;
	}

}
