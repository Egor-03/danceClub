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
import by.grsu.anikevich.danceClub.web.dto.SortDto;
import by.grsu.anikevich.danceClub.web.dto.TableStateDto;

public class GroupeDaoImpl extends AbstractDao implements IDao<Integer, Groupe>{

	public static final GroupeDaoImpl INSTANCE = new GroupeDaoImpl();

	private GroupeDaoImpl() {
		super();
	}

	@Override
	public void insert(Groupe entity) {
		try (Connection c = createConnection()) {
			PreparedStatement pstmt = c.prepareStatement(
					"insert into groupe(name,person_id,section_id,data,state_id) values(?,?,?,?,?)");
			pstmt.setString(1, entity.getName());
			pstmt.setInt(2, entity.getPersonId());
			pstmt.setInt(3, entity.getSectionId());
			pstmt.setDate(4, entity.getData());
			pstmt.setInt(5, entity.getStateId());
			pstmt.executeUpdate();
			entity.setId(getGeneratedId(c, "groupe"));
		} catch (SQLException e) {
			throw new RuntimeException("can't insert Groupe entity", e);
		}

	}

	@Override
	public void update(Groupe entity) {
		try (Connection c = createConnection()) {
			PreparedStatement pstmt = c.prepareStatement(
					"update groupe set  name=?, data =? where id =? ");
			
			pstmt.setString(1, entity.getName());
			pstmt.setDate(2, entity.getData());
			pstmt.setInt(3, entity.getId());
			pstmt.executeUpdate();
			entity.setId(getGeneratedId(c, "groupe"));
		} catch (SQLException e) {
			throw new RuntimeException("can't update Groupe entity", e);
		}
	}

	@Override
	public void delete(Integer id) {
		try (Connection c = createConnection()) {
			PreparedStatement pstmt = c.prepareStatement("delete from groupe where id=?");
			pstmt.setInt(1, id);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException("can't delete Groupe entity", e);
		}
	}

	@Override
	public Groupe getById(Integer id) {
		Groupe entity = null;
		try (Connection c = createConnection()) {
			PreparedStatement pstmt = c.prepareStatement("select * from groupe where id=?");
			pstmt.setInt(1, id);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				entity = rowToEntity(rs);
			}
		} catch (SQLException e) {
			throw new RuntimeException("can't get Groupe entity by id", e);
		}

		return entity;
	}

	@Override
	public List<Groupe> getAll() {
		List<Groupe> entitiesList = new ArrayList<>();
		try (Connection c = createConnection()) {
			ResultSet rs = c.createStatement().executeQuery("select * from groupe");
			while (rs.next()) {
				Groupe entity = rowToEntity(rs);
				entitiesList.add(entity);
			}
		} catch (SQLException e) {
			throw new RuntimeException("can't select Groupe entities", e);
		}

		return entitiesList;
	}

	private Groupe rowToEntity(ResultSet rs) throws SQLException {
		Groupe entity = new Groupe();
		entity.setId(rs.getInt("id"));
		entity.setName(rs.getString("name"));
		entity.setPersonId(rs.getInt("person_id"));
		entity.setSectionId(rs.getInt("section_id"));
		entity.setData(rs.getDate("data"));
		entity.setStateId(rs.getInt("state_id"));
		return entity;
	}
	



	public List<Groupe> getAllwithId(Integer id) {
		List<Groupe> entitiesList = new ArrayList<>();
		try (Connection c = createConnection()) {
			PreparedStatement pstmt = c.prepareStatement("select * from groupe where person_id=?");
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Groupe entity = rowToEntity(rs);
				entitiesList.add(entity);
			}
		} catch (SQLException e) {
			throw new RuntimeException("can't select Groupe entities", e);
		}

		return entitiesList;
		
	}

	@Override
	public List<Groupe> find(TableStateDto tableStateDto) {
		List<Groupe> entitiesList = new ArrayList<>();
		try (Connection c = createConnection()) {
			StringBuilder sql = new StringBuilder("select * from groupe");

			final SortDto sortDto = tableStateDto.getSort();
			if (sortDto != null) {
				sql.append(String.format(" order by %s %s", sortDto.getColumn(), resolveSortOrder(sortDto)));
			}

			sql.append(" limit " + tableStateDto.getItemsPerPage());
			sql.append(" offset " + resolveOffset(tableStateDto));

			System.out.println("searching Requests using SQL: " + sql);
			ResultSet rs = c.createStatement().executeQuery(sql.toString());
			while (rs.next()) {
				Groupe entity = rowToEntity(rs);
				entitiesList.add(entity);
			}
		} catch (SQLException e) {
			throw new RuntimeException("can't select Groupe entities", e);
		}
		return entitiesList;
	}

	@Override
	public int count() {
		try (Connection c = createConnection()) {
			PreparedStatement pstmt = c.prepareStatement("select count(*) as c from groupe");
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			return rs.getInt("c");
		} catch (SQLException e) {
			throw new RuntimeException("can't get Groupe count", e);
		}
	}

	@Override
	public List<Groupe> getAllwithselfId(Integer id) {
		List<Groupe> entitiesList = new ArrayList<>();
		try (Connection c = createConnection()) {
			PreparedStatement pstmt = c.prepareStatement("select * from groupe where id=?");
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Groupe entity = rowToEntity(rs);
				entitiesList.add(entity);
			}
		} catch (SQLException e) {
			throw new RuntimeException("can't select Groupe entities", e);
		}

		return entitiesList;
		
	}


}
