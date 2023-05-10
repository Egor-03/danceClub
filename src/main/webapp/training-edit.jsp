<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="Training edit" scope="application" />
<t:wrapper>
	<c:choose>
		<c:when test="${empty dto.id}">
		<div class="row">
				<div class="col s12">
					<div class="center-align">
						<h1>Create training</h1>
					</div>
				</div>
			</div>
		</c:when>
		<c:otherwise>
		<div class="row">
				<div class="col s12">
					<div class="center-align">
						<h1>Edit training #${dto.id}</h1>
					</div>
				</div>
			</div>
			
		</c:otherwise>
	</c:choose>
	<form class="col s12" method="post" action="/training">
		<div class="row">
			<input type="hidden" name="id" value="${dto.id}" />
			<div class="row">
				<div class="col s5">
					<label for="placeId">PlaceId</label> <select name="placeId"
						class="browser-default" required>
						<option value="">--select Place --</option>
						<c:forEach items="${allPlaces}" var="place">
							<option value="${place.id}"
								<c:if test="${place.id eq dto.placeId}">selected="selected"</c:if>>${place.name}</option>
						</c:forEach>
					</select>
				</div>
			</div>
		</div>
		<div class="row">
			
			<div class="col s5">
					<label for="coachId">CoachId</label> <select name="coachId"
						class="browser-default" required>
						<option value="">--select Coach --</option>
						<c:forEach items="${allCoachs}" var="coach">
							<option value="${coach.id}"
								<c:if test="${coach.id eq dto.coachId}">selected="selected"</c:if>>${coach.firstName} ${coach.secondName}</option>
						</c:forEach>
					</select>
				</div>
			<div class="col s5">
					<label for="groupeId">GroupeId</label> <select name="groupeId"
						class="browser-default" required>
						<option value="">--select Groupe --</option>
						<c:forEach items="${allGroups}" var="groupe">
							<option value="${groupe.id}"
								<c:if test="${groupe.id eq dto.groupeId}">selected="selected"</c:if>>${groupe.name}</option>
						</c:forEach>
					</select>
				</div>
			<div class="input-field col s3">
				<input type="text" name="data" required value="${dto.data}"> <label
					for="data">Data</label>
			</div>
		</div>
		</div>

		<div class="row">
			<div class="col s12 input-field center-align">
				<a class="btn waves-effect waves-light red" href="/training"><i
					class="material-icons left">list</i>back</a>&nbsp;
				<button class="btn waves-effect waves-light" type="submit">
					<i class="material-icons left">save</i>save
				</button>
			</div>
		</div>
	</form>



</t:wrapper>