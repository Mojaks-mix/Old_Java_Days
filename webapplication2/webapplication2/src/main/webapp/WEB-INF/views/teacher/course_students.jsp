<%@ include file="/WEB-INF/views/inc/header.jsp" %>
<%@ include file="/WEB-INF/views/teacher/grad_student.jsp" %>
<h1 class="text-center my-5 py-3">Students Grades</h1>

<div class="container">
    <div class="row">
        <div class="col-10 mx-auto p-4 border mb-5">
            <table class="table">
                <thead class="thead-dark">
                    <tr>
                        <th scope="col">Student Name</th>
                        <th scope="col">First</th>
                        <th scope="col">Second</th>
                        <th scope="col">Final</th>
                        <th scope="col">Symbol</th>
                        <th scope="col">Mark</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="entry" items="${grads}">
                        <!-- Access the course name (key) -->
                        <c:set var="studentName" value="${entry.key}" />
                        <!-- Access the Grad object (value) -->
                        <c:set var="grad" value="${entry.value}" />
                        <tr>
                            <td>${studentName}</td>
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
                            <td><c:out value="${grad.symbol}" /></td>
                            <td>
                                <button type="button" data-course="${grad.courseId}" data-student="${grad.studentId}" class="markStudentBtn btn btn-info float-end">
                                    Mark
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

 $(document).on('click', '.markStudentBtn', function () {
     var courseId = $(this).data('course');
     var studentId = $(this).data('student');
     $.ajax( {
         type: "GET",
         url: "/teacher/mark-student?courseId=" + courseId + "&studentId=" + studentId,
         success: function (response) {
                 $('#studentId').val(response.studentId);
                 $('#courseId').val(response.courseId);
                 $('#firstMark').val(response.firstMark);
                 $('#secondMark').val(response.secondMark);
                 $('#finalMark').val(response.finalMark);
                 $('#symbol').val(response.symbol);

                 $('#markStudentModal').modal('show');
              }
     } );

  } );

$(document).on('submit', '#mark_student', function (event) {
    event.preventDefault();

    var formData = $(this).serialize(); // Serialize the form data

    formData += '&updated=true'; // Append additional data

    $.ajax( {
        type: "POST",
        url: "/teacher/update-mark",
        data: formData,
        processData: false,
        success: function (response) {
            var res = $.parseJSON(response);
            if(res.status == 500) {
                $('#errormessage').removeClass('d-none');
                $('#errormessage').text(res.message);
             }
            else if(res.status == 200) {
                $('#errormessage').addClass('d-none');
                $('#markStudentModal').modal('hide');
                $('#mark_student')[0].reset();
                location.reload();
             }
         }
    } );

 } );

</script>
<%@ include file="/WEB-INF/views/inc/footer.jsp" %>