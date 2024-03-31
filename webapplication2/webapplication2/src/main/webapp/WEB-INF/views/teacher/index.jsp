<%@ include file="/WEB-INF/views/inc/header.jsp" %>
<div class="container">
    <div class="row">
        <div class="col-10 mx-auto p-4 border mb-5">
            <table class="table">
                <thead class="thead-dark">
                    <tr>
                        <th scope="col" align="center">Course Name</th>
                        <th scope="col"></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="course" items="${courses}">
                        <tr>
                            <td align="center">${course.courseName}</td>
                            <td align="center">
                                <button type="button" data-id="${course.courseId}" class="ViewCourseBtn btn btn-info float-end">
                                    View
                                </button>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
<script>
$(document).on('click', '.ViewCourseBtn', function (event) {
    event.preventDefault();
    var id = $(this).data('id');
    window.location.href = '/teacher/course-students?courseId=' + id;
 } );
</script>
<%@ include file="/WEB-INF/views/inc/footer.jsp" %>