<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">

</head>
<body>

<header>
    <nav class="navbar navbar-expand-md navbar-dark" >
        <div>
            <a href="" class="navbar-brand">User Management</a>
        </div>
        <ul class="navbar-nav">
            <li><A href="<%=request.getContextPath()%>/list" class="nav-link">User</A> </li>
        </ul>
    </nav>
</header>
<br>
<div class="container col-md-5">
    <div class="card">
        <div class="card-body">
            <c:if test="${user != null}">
            <form action="update" method="post">
                </c:if>
                <c:if test="${user == null}">
                <form action="insert" method="post">
                    </c:if>
                    <caption>
                        <h2>
                            <c:if test="${user != null}">
                                Edit User
                            </c:if>
                            <c:if test="${user== null}">
                                Add User
                            </c:if>

                        </h2>
                    </caption>
                    <c:if test="${user != null}">
                        <input type="hidden" name="id" value="<c:out value="${user.id}"/>"/>
                    </c:if>
                    <fieldset class="from-group">
                        <label>User name</label><input type="text" value="<c:out value="${user.name}"/>
"                                               class="from-control" name="name" required="required">
                    </fieldset>
                    <fieldset class="from-group">
                        <label>User email</label><input type="text" value="<c:out value="${user.email}"/>
"                                               class="from-control" name="email" required="required">
                    </fieldset>
                    <fieldset class="from-group">
                        <label>User country</label><input type="text" value="<c:out value="${user.country}"/>
"                                               class="from-control" name="country" required="required">
                    </fieldset>
                    <button type="submit" class="btn btn-success">Save</button>
                </form>

            </form>

        </div>
    </div>
</div>
</body>
</html>