<%@ include file="/WEB-INF/views/inc/header.jsp" %>
<div class="container">
    <div class="row">
        <div class="col-10 mx-auto p-4 border mb-5">
            <div class="card-header">
                <h4>
                    ${courseName}
                </h4>
            </div>
            <c:forEach var="entry" items="${courseInfo}">
                <table class="table">
                    <thead class="thead-dark">
                        <tr align="center">
                            <th scope="col">Instructor: ${entry.key.userName}</th>
                        </tr>
                        <tr  align="center">
                            <th scope="col">Student Name</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="student" items="${entry.value}">
                            <tr>
                                <td align="center">${student.userName}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:forEach>
            <div class="card-footer" align="center">
                <h4>
                    <a href="http://localhost:8080/admin/users">
                        <button type="button" class="btn btn-secondary"> View Users </button>
                    </a>
                </h4>

                <h4>
                    <a href="http://localhost:8080/admin/courses">
                        <button type="button" class="btn btn-secondary"> View Courses </button>
                    </a>
                </h4>
            </div>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/views/inc/footer.jsp" %>