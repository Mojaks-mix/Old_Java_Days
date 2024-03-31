<%@ include file="/WEB-INF/views/inc/header.jsp" %>

<h1 class="text-center my-5 py-3">Semester Grads</h1>

<div class="container">
    <div class="row">
        <div class="col-10 mx-auto p-4 border mb-5">
            <table class="table">
                <thead class="thead-dark">
                    <tr>
                        <th scope="col">Course Name</th>
                        <th scope="col">First</th>
                        <th scope="col">Second</th>
                        <th scope="col">Final</th>
                        <th scope="col">Symbol</th>
                        <th scope="col">Drop</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="entry" items="${grads}">
                        <!-- Access the course name (key) -->
                        <c:set var="courseName" value="${entry.key}" />
                        <!-- Access the Grad object (value) -->
                        <c:set var="grad" value="${entry.value}" />
                        <tr>
                            <td>${courseName}</td>
                            <td>
                                <c:if test="${grad.firstMark >= 0}">
                                    ${grad.firstMark}
                                </c:if>
                            </td>
                            <td>
                                <c:if test="${grad.secondMark >= 0}">
                                    ${grad.secondMark}
                                </c:if>
                            </td>
                            <td>
                                <c:if test="${grad.finalMark >= 0}">
                                    ${grad.finalMark}
                                </c:if>
                            </td>
                            <td>${grad.symbol}</td>
                            <td>
                                <button type="button" data-id="${grad.courseId}" class="DropCourseBtn btn btn-danger float-end">
                                    Drop
                                </button>
                            </td>
                        </tr>
                    </c:forEach>
                    <tr align="center">
                        <td>Average</td>
                        <c:if test="${average >= 0}">
                            <td>${average}</td>
                        </c:if>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>
<script>
$(document).on('click', '.DropCourseBtn', function (event) {
    event.preventDefault();

    if(confirm('Are you sure you want to drop this course?')) {
        var id = $(this).data('id');

        $.ajax( {
            type: "POST",
            url: "/student/drop?courseId=" + id,
            success: function (response) {
                var res = $.parseJSON(response);
                if(res.status == 500) {
                    alert(res.message);
                 }
                else if(res.status == 200) {
                    alert(res.message);
                    location.reload();
                 }
             }
        } );
     }
 } );

</script>
<%@ include file="/WEB-INF/views/inc/footer.jsp" %>
