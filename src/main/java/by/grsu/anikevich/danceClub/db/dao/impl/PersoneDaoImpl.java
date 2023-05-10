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
import by.grsu.anikevich.danceClub.db.model.Persone;
import by.grsu.anikevich.danceClub.web.dto.SortDto;
import by.grsu.anikevich.danceClub.web.dto.TableStateDto;

public class PersoneDaoImpl extends AbstractDao implements IDao<Integer, Persone> {

	public static final PersoneDaoImpl INSTANCE = new PersoneDaoImpl();

	private PersoneDaoImpl() {
		super();
	}

	@Override
	public void insert(Persone entity) {
		try (Connection c = createConnection()) {
			PreparedStatement pstmt = c.prepareStatement(
					"insert into persone(role_id,first_name,second_name,patronymic,mail) values(?,?,?,?,?)");
			pstmt.setInt(1, entity.getRoleId());
			pstmt.setString(2, entity.getFirstName());
			pstmt.setString(3, entity.getSecondName());
			pstmt.setString(4, entity.getPatronymic());
			pstmt.setString(5, entity.getMail());
			pstmt.executeUpdate();
			entity.setId(getGeneratedId(c, "persone"));
		} catch (SQLException e) {
			throw new RuntimeException("can't insert Persone entity", e);
		}

	}

	@Override
	public void update(Persone entity) {
		try (Connection c = createConnection()) {
			PreparedStatement pstmt = c.prepareStatement(
					"update persone set  first_name=?, second_name=?, patronymic=?, mail=?  where id =? ");
			//pstmt.setInt(1, entity.getRoleId());
			pstmt.setString(1, entity.getFirstName());
			pstmt.setString(2, entity.getSecondName());
			pstmt.setString(3, entity.getPatronymic());
			pstmt.setString(4, entity.getMail());
			pstmt.setInt(5, entity.getId());
			pstmt.executeUpdate();
			entity.setId(getGeneratedId(c, "persone"));
		} catch (SQLException e) {
			throw new RuntimeException("can't update Persone entity", e);
		}
	}

	@Override
	public void delete(Integer id) {
		try (Connection c = createConnection()) {
			PreparedStatement pstmt = c.prepareStatement("delete from persone where id=?");
			pstmt.setInt(1, id);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException("can't delete Persone entity", e);
		}
	}

	@Override
	public Persone getById(Integer id) {
		Persone entity = null;
		try (Connection c = createConnection()) {
			PreparedStatement pstmt = c.prepareStatement("select * from persone where id=?");
			pstmt.setInt(1, id);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				entity = rowToEntity(rs);
			}
		} catch (SQLException e) {
			throw new RuntimeException("can't get Persone entity by id", e);
		}

		return entity;
	}

	@Override
	public List<Persone> getAll() {
		List<Persone> entitiesList = new ArrayList<>();
		try (Connection c = createConnection()) {
			ResultSet rs = c.createStatement().executeQuery("select * from persone");
			while (rs.next()) {
				Persone entity = rowToEntity(rs);
				entitiesList.add(entity);
			}
		} catch (SQLException e) {
			throw new RuntimeException("can't select Persone entities", e);
		}

		return entitiesList;
	}

	private Persone rowToEntity(ResultSet rs) throws SQLException {
		Persone entity = new Persone();
		entity.setId(rs.getInt("id"));
		entity.setRoleId(rs.getInt("role_id"));
		entity.setFirstName(rs.getString("first_name"));
		entity.setSecondName(rs.getString("second_name"));
		entity.setPatronymic(rs.getString("patronymic"));
		entity.setMail(rs.getString("mail"));
		return entity;
	}

	@Override
	public List<Persone> getAllwithId(Integer id) {
		List<Persone> entitiesList = new ArrayList<>();
		try (Connection c = createConnection()) {
			PreparedStatement pstmt = c.prepareStatement("select * from persone where id=?");
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Persone entity = rowToEntity(rs);
				entitiesList.add(entity);
			}
		} catch (SQLException e) {
			throw new RuntimeException("can't select Groupe entities", e);
		}

		return entitiesList;
		
	}

	@Override
	public List<Persone> find(TableStateDto tableStateDto) {
		List<Persone> entitiesList = new ArrayList<>();
		try (Connection c = createConnection()) {
			StringBuilder sql = new StringBuilder("select * from persone");

			final SortDto sortDto = tableStateDto.getSort();
			if (sortDto != null) {
				sql.append(String.format(" order by %s %s", sortDto.getColumn(), resolveSortOrder(sortDto)));
			}

			sql.append(" limit " + tableStateDto.getItemsPerPage());
			sql.append(" offset " + resolveOffset(tableStateDto));

			System.out.println("searching Requests using SQL: " + sql);
			ResultSet rs = c.createStatement().executeQuery(sql.toString());
			while (rs.next()) {
				Persone entity = rowToEntity(rs);
				entitiesList.add(entity);
			}
		} catch (SQLException e) {
			throw new RuntimeException("can't select Persone entities", e);
		}
		return entitiesList;
	}

	@Override
	public int count() {
		try (Connection c = createConnection()) {
			PreparedStatement pstmt = c.prepareStatement("select count(*) as c from persone");
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			return rs.getInt("c");
		} catch (SQLException e) {
			throw new RuntimeException("can't get Persone count", e);
		}
	}

	@Override
	public List<Groupe> getAllwithselfId(Integer parseInt) {
		// TODO Auto-generated method stub
		return null;
	}

}
