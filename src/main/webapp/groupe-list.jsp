<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<c:set var="pageTitle" value="Persone list" scope="application"/>
<%@ taglib prefix="mytaglib" uri="my-custom-tags-uri"%>
<c:set var="pageUrl" value="/groupe" scope="page" />
<t:wrapper>
		
	<div class="row">
		<div class="col s12">
			<div class="center-align">
			<h1>Groupe list</h1>
				<a class="btn-floating btn-large waves-effect waves-light" href="/groupe?view=edit"><i class="material-icons">add</i></a>
			</div>
		</div>
	</div>
	<table>
		<thead>
			<tr>
				<th><mytaglib:sort-link pageUrl="${pageUrl}" column="id">id</mytaglib:sort-link></th>
				<th>Name</th>
				<th>Persone name</th>
				<th>Section name</th>
				<th>State name</th>
				<th><mytaglib:sort-link pageUrl="${pageUrl}" column="data">Date</mytaglib:sort-link></th>
				<th>actions</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="entity" items="${list}" varStatus="loopCounter">
				<tr>
					<td><c:out value="${entity.id}" /></td>
					<td><c:out value="${entity.name}" /></td>
					<td><c:out value="${entity.personeName}" /></td>
					<td><c:out value="${entity.sectionName}" /></td>
					<td><c:out value="${entity.stateName}" /></td>
					<td><c:out value="${entity.data}" /></td>
					<td><a class="btn-small btn-floating waves-effect waves-light blue" title="редактировать" href="/groupe?view=edit&id=${entity.id}"><i
							class="material-icons">edit</i></a>
							<a class="btn-small btn-floating waves-effect waves-light black" title="Расписание" href="/training?groupeId=${entity.id}" ><i class="material-icons">list</i></a>
							<a class="btn-small btn-floating waves-effect waves-light red" title="удалить" onclick="sendHTTPDelete('/groupe?id=${entity.id}')"><i class="material-icons">delete</i></a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<t:paging/>	
	
	
</t:wrapper>