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
import by.grsu.anikevich.danceClub.db.model.Section;
import by.grsu.anikevich.danceClub.web.dto.SortDto;
import by.grsu.anikevich.danceClub.web.dto.TableStateDto;

public class SectionDaoImpl extends AbstractDao implements IDao<Integer, Section> {

	public static final SectionDaoImpl INSTANCE = new SectionDaoImpl();

	private SectionDaoImpl() {
		super();
	}

	@Override
	public void insert(Section entity) {
		try (Connection c = createConnection()) {
			PreparedStatement pstmt = c.prepareStatement("insert into section(section_name) values(?)");
			pstmt.setString(1, entity.getName());
			pstmt.executeUpdate();
			entity.setId(getGeneratedId(c, "section"));
		} catch (SQLException e) {
			throw new RuntimeException("can't insert Section entity", e);
		}

	}

	@Override
	public void update(Section entity) {
		try (Connection c = createConnection()) {
			PreparedStatement pstmt = c.prepareStatement("update section set section_name=? where id=?");
			pstmt.setString(1, entity.getName());
			pstmt.setInt(2,entity.getId());
			pstmt.executeUpdate();
			entity.setId(getGeneratedId(c, "section"));
		} catch (SQLException e) {
			throw new RuntimeException("can't update Section entity", e);
		}
	}

	@Override
	public void delete(Integer id) {
		try (Connection c = createConnection()) {
			PreparedStatement pstmt = c.prepareStatement("delete from section where id=?");
			pstmt.setInt(1, id);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException("can't delete Section entity", e);
		}
	}

	@Override
	public Section getById(Integer id) {
		Section entity = null;
		try (Connection c = createConnection()) {
			PreparedStatement pstmt = c.prepareStatement("select * from section where id=?");
			pstmt.setInt(1, id);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				entity = rowToEntity(rs);
			}
		} catch (SQLException e) {
			throw new RuntimeException("can't get Section entity by id", e);
		}

		return entity;
	}

	@Override
	public List<Section> getAll() {
		List<Section> entitiesList = new ArrayList<>();
		try (Connection c = createConnection()) {
			ResultSet rs = c.createStatement().executeQuery("select * from section");
			while (rs.next()) {
				Section entity = rowToEntity(rs);
				entitiesList.add(entity);
			}
		} catch (SQLException e) {
			throw new RuntimeException("can't select Section entities", e);
		}

		return entitiesList;
	}

	private Section rowToEntity(ResultSet rs) throws SQLException {
		Section entity = new Section();
		entity.setId(rs.getInt("id"));
		entity.setName(rs.getString("section_name"));
		return entity;
	}

	@Override
	public List<Section> getAllwithId(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override

	public List<Section> find(TableStateDto tableStateDto) {
		List<Section> entitiesList = new ArrayList<>();
		try (Connection c = createConnection()) {
			StringBuilder sql = new StringBuilder("select * from section");

			final SortDto sortDto = tableStateDto.getSort();
			if (sortDto != null) {
				sql.append(String.format(" order by %s %s", sortDto.getColumn(), resolveSortOrder(sortDto)));
			}

			sql.append(" limit " + tableStateDto.getItemsPerPage());
			sql.append(" offset " + resolveOffset(tableStateDto));

			System.out.println("searching Requests using SQL: " + sql);
			ResultSet rs = c.createStatement().executeQuery(sql.toString());
			while (rs.next()) {
				Section entity = rowToEntity(rs);
				entitiesList.add(entity);
			}
		} catch (SQLException e) {
			throw new RuntimeException("can't select Section entities", e);
		}
		return entitiesList;
	}

	@Override
	public int count() {
		try (Connection c = createConnection()) {
			PreparedStatement pstmt = c.prepareStatement("select count(*) as c from section");
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			return rs.getInt("c");
		} catch (SQLException e) {
			throw new RuntimeException("can't get Section count", e);
		}
	}

	@Override
	public List<Groupe> getAllwithselfId(Integer parseInt) {
		// TODO Auto-generated method stub
		return null;
	}

}
