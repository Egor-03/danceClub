package by.grsu.anikevich.danceClub.db.dao;

import java.util.List;

import by.grsu.anikevich.danceClub.db.model.Groupe;
import by.grsu.anikevich.danceClub.web.dto.TableStateDto;

public interface IDao<ID, TYPE> {
	void insert(TYPE t);

	void update(TYPE t);

	void delete(ID id);

	TYPE getById(ID id);

	List<TYPE> getAll();
	List<TYPE> getAllwithId(ID id);
	List<TYPE> find(TableStateDto tableStateDto);

	int count();

	List<Groupe> getAllwithselfId(Integer parseInt);
}
