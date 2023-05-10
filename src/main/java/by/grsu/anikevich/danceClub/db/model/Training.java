package by.grsu.anikevich.danceClub.db.model;

import java.sql.Date;

public class Training {
	private Integer id;
	private Integer placeId;
	private Integer coachId;
	private Integer groupeId;
	private Date  data;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getPlaceId() {
		return placeId;
	}
	public void setPlaceId(Integer placeId) {
		this.placeId = placeId;
	}
	public Integer getCoachId() {
		return coachId;
	}
	public void setCoachId(Integer coachId) {
		this.coachId = coachId;
	}
	public Integer getGroupeId() {
		return groupeId;
	}
	public void setGroupeId(Integer groupId) {
		this.groupeId = groupId;
	}
	public Date getData() {
		return data;
	}
	public void setDate(Date data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "Training [id=" + id + ", placeId=" + placeId + ", coachId=" + coachId + ", groupeId=" + groupeId
				+ ", date=" + data + "]";
	}
}
