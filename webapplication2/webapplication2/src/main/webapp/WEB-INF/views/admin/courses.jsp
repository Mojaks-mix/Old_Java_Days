<%@ include file="/WEB-INF/views/inc/header.jsp" %>
<%@ include file="/WEB-INF/views/admin/add_course.jsp" %>
<%@ include file="/WEB-INF/views/admin/edit_course.jsp" %>
<div class="container">
    <div class="row">
        <div class="col-10 mx-auto p-4 border mb-5">
            <div class="card-header">
                <h4>
                    Courses
                    <button type="button" class="addCourseBtn btn btn-secondary float-right"> Add Course </button>
                </h4>
            </div>
            <table class="table">
                <thead class="thead-dark">
                    <tr  align="center">
                        <th scope="col">Course Name</th>
                        <th scope="col">Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="course" items="${courses}">
                        <tr>
                            <td align="center">${course.courseName}</td>
                            <td align="center">
                                <button type="button" data-id="${course.courseId}" class="editCourseBtn btn btn-info float-end">
                                    Edit
                                </button>

                                <button type="button" data-id="${course.courseId}" class="deleteCourseBtn btn btn-danger float-end">
                                    Delete
                                </button>

                                <a href="http://localhost:8080/admin/course-details?courseId=${course.courseId}">
                                    <button type="button" class="btn btn-secondary float-end">
                                        Details
                                    </button>
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <div class="card-footer" align="center">
                <h4>
                    <a href="http://localhost:8080/admin/users">
                        <button type="button" class="btn btn-secondary"> View Users </button>
                    </a>
                </h4>
            </div>
        </div>
    </div>
</div>
<script>
$(document).on('click', '.deleteCourseBtn', function (event) {
    event.preventDefault();

    if(confirm('Are you sure you want to delete this course?')) {
        var id = $(this).data('id');

        $.ajax( {
            type: "POST",
            url: "/admin/delete-course?courseId=" + id,
            success: function (response) {
                if(response.status == 500) {
                    alert(response.message);
                 }
                else if(response.status == 200) {
                    alert(response.message);
                    location.reload();
                 }
             }
        } );
     }
 } );

 $(document).on('click', '.addCourseBtn', function () {
    $('#addCourseModal').modal('show');
 } );

 $(document).on('submit', '#add_course', function (event) {
     event.preventDefault();

     var formData = $(this).serialize(); // Serialize the form data

     $.ajax( {
         type: "POST",
         url: "/admin/add-course",
         data: formData,
         processData: false,
         success: function (response) {
             if(response.status === 500) {
                 $('#errormessage').removeClass('d-none');
                 $('#errormessage').text(response.message);
              }
             else if(response.status === 200) {
                 $('#errormessage').addClass('d-none');
                 $('#addCourseModal').modal('hide');
                 $('#add_course')[0].reset();
                 location.reload();
              }
          }
     } );

  } );

  $(document).on('click', '.editCourseBtn', function () {
       var courseId = $(this).data('id');
       $.ajax( {
           type: "GET",
           url: "/admin/edit-course?courseId=" + courseId,
           success: function (response) {
                $('#course_name').val(response.courseName);
                $('#courseId').val(response.courseId);

                $('#editCourseModal').modal('show');
                }
       } );

    } );

$(document).on('submit', '#update_course', function (event) {
     event.preventDefault();

     var formData = $(this).serialize(); // Serialize the form data

     $.ajax( {
         type: "POST",
         url: "/admin/update-course",
         data: formData,
         processData: false,
         success: function (response) {
             if(response.status === 500) {
                 $('#errormessage').removeClass('d-none');
                 $('#errormessage').text(response.message);
              }
             else if(response.status === 200) {
                 $('#errormessage').addClass('d-none');
                 $('#editCourseModal').modal('hide');
                 $('#update_course')[0].reset();
                 location.reload();
              }
          }
     } );

  } );
</script>
<%@ include file="/WEB-INF/views/inc/footer.jsp" %>