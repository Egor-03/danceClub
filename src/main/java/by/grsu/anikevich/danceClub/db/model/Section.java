package by.grsu.anikevich.danceClub.db.model;

public class Section {
	private Integer id;
	private String name;
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
	@Override
	public String toString() {
		return "Section [id=" + id + ", name=" + name + "]";
	}
	
}
