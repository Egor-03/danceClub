<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<c:set var="pageTitle" value="Persone list" scope="application"/>
<%@ taglib prefix="mytaglib" uri="my-custom-tags-uri"%>
<c:set var="pageUrl" value="/training" scope="page" />
<t:wrapper>
		
	<div class="row">
		<div class="col s12">
			<div class="center-align">
			<h1>Training list</h1>
				<a class="btn-floating btn-large waves-effect waves-light" href="/training?view=edit"><i class="material-icons">add</i></a>
			</div>
		</div>
	</div>
	<table>
		<thead>
			<tr>
				<th><mytaglib:sort-link pageUrl="${pageUrl}" column="id">id</mytaglib:sort-link></th>
				
				<th>Place</th>
				<th>Coach name</th>
				<th>Groupe name</th>
				<th><mytaglib:sort-link pageUrl="${pageUrl}" column="data">Date</mytaglib:sort-link></th>
				<th>actions</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="entity" items="${list}" varStatus="loopCounter">
				<tr>
					<td><c:out value="${entity.id}" /></td>
					<td><c:out value="${entity.placeName}" /></td>
					<td><c:out value="${entity.coachName}" /></td>
					<td><c:out value="${entity.groupeName}" /></td>
					<td><c:out value="${entity.data}" /></td>
					<td><a class="btn-small btn-floating waves-effect waves-light blue" title="редактировать" href="/training?view=edit&id=${entity.id}"><i
							class="material-icons">edit</i></a><a class="btn-small btn-floating waves-effect waves-light red" title="удалить" onclick="sendHTTPDelete('/training?id=${entity.id}')"><i class="material-icons">delete</i></a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<t:paging/>	
	
	
</t:wrapper>