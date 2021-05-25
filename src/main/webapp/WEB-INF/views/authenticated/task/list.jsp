<%--
- list.jsp
-
- Copyright (C) 2012-2021 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:list>
	<acme:list-column code="authenticated.task.list.label.title" path="title" width="30%" />
	<acme:list-column code="authenticated.task.list.label.workload" path="workload" width="15%" />
	<acme:list-column code="authenticated.task.list.label.link" path="link" width="15%" />
	<acme:list-column code="authenticated.task.list.label.start_period" path="startPeriod" width="15%" />
	<acme:list-column code="authenticated.task.list.label.end_period" path="endPeriod" width="15%" />
	<acme:list-column code="authenticated.task.list.label.visibility" path="visibility" width="10%" />
</acme:list>


