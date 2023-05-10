package by.grsu.anikevich.danceClub.web.dto;

import java.sql.Date;

public class TrainingDto {
	private Integer id;
	private Integer placeId;
	private String placeName;
	private Integer coachId;
	private String coachName;
	private Integer groupeId;
	private String groupeName;
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
	public String getPlaceName() {
		return placeName;
	}
	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}
	public Integer getCoachId() {
		return coachId;
	}
	public void setCoachId(Integer coachId) {
		this.coachId = coachId;
	}
	public String getCoachName() {
		return coachName;
	}
	public void setCoachName(String coachName) {
		this.coachName = coachName;
	}
	public Integer getGroupeId() {
		return groupeId;
	}
	public void setGroupeId(Integer groupeId) {
		this.groupeId = groupeId;
	}
	public String getGroupeName() {
		return groupeName;
	}
	public void setGroupeName(String groupeName) {
		this.groupeName = groupeName;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}

}
