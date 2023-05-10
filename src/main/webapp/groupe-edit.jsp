<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="Groupe edit" scope="application" />
<t:wrapper>
	<c:choose>
		<c:when test="${empty dto.id}">
		<div class="row">
				<div class="col s12">
					<div class="center-align">
						<h1>Create groupe</h1>
					</div>
				</div>
			</div>
		</c:when>
		<c:otherwise>
		<div class="row">
				<div class="col s12">
					<div class="center-align">
						<h1>Edit groupe #${dto.id}</h1>
					</div>
				</div>
			</div>
			
		</c:otherwise>
	</c:choose>
	<form class="col s12" method="post" action="/groupe">
		<div class="row">
			<div class="input-field col s3">
				<input type="text" name="name" required value="${dto.name}">
				<label for="GroupeName">Groupe Name</label>
			</div>
			<input type="hidden" name="id" value="${dto.id}" />
			<div class="row">
				<div class="col s5">
					<label for="personId">PersoneId</label> <select name="personId"
						class="browser-default" required>
						<option value="">--select Persone --</option>
						<c:forEach items="${allPersons}" var="persone">
							<option value="${persone.id}"
								<c:if test="${persone.id eq dto.personId}">selected="selected"</c:if>>${persone.firstName} ${persone.secondName}</option>
						</c:forEach>
					</select>
				</div>
			</div>
		</div>
		<div class="row">
			
			<div class="col s5">
					<label for="sectionId">SectionId</label> <select name="sectionId"
						class="browser-default" required>
						<option value="">--select Role --</option>
						<c:forEach items="${allSections}" var="section">
							<option value="${section.id}"
								<c:if test="${section.id eq dto.sectionId}">selected="selected"</c:if>>${section.name}</option>
						</c:forEach>
					</select>
				</div>
			<div class="col s5">
					<label for="stateId">StateId</label> <select name="stateId"
						class="browser-default" required>
						<option value="">--select state --</option>
						<c:forEach items="${allStates}" var="state">
							<option value="${state.id}"
								<c:if test="${state.id eq dto.stateId}">selected="selected"</c:if>>${state.name}</option>
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
				<a class="btn waves-effect waves-light red" href="/groupe"><i
					class="material-icons left">list</i>back</a>&nbsp;
				<button class="btn waves-effect waves-light" type="submit">
					<i class="material-icons left">save</i>save
				</button>
			</div>
		</div>
	</form>



</t:wrapper>