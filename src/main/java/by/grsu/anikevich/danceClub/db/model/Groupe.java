package by.grsu.anikevich.danceClub.db.model;

import java.sql.Date;

public class Groupe {
	private Integer id;
	private String name;
	private Integer personId;
	private Integer sectionId;
	private Date data;
	private Integer stateId;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getPersonId() {
		return personId;
	}
	public void setPersonId(Integer personId) {
		this.personId = personId;
	}
	public Integer getSectionId() {
		return sectionId;
	}
	public void setSectionId(Integer sectionId) {
		this.sectionId = sectionId;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public Integer getStateId() {
		return stateId;
	}
	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}
	@Override
	public String toString() {
		return "Group [id=" + id + ", name=" + name + ", personId=" + personId + ", sectionId=" + sectionId + ", date="
				+ data + ", stateId=" + stateId + "]";
	}
	
}
