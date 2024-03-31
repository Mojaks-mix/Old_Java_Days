<%@ include file="/WEB-INF/views/inc/header.jsp" %>
<%@ include file="/WEB-INF/views/admin/add_user.jsp" %>
<%@ include file="/WEB-INF/views/admin/edit_user.jsp" %>
<div class="container">
    <div class="row">
        <div class="col-10 mx-auto p-4 border mb-5">
            <div class="card-header">
                <h4>
                    Users
                    <button type="button" class="addUserBtn btn btn-secondary float-right"> Add User </button>
                </h4>
            </div>
            <table class="table">
                <thead class="thead-dark">
                    <tr  align="center">
                        <th scope="col">User Name</th>
                        <th scope="col">Role</th>
                        <th scope="col">Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="user" items="${users}">
                        <tr>
                            <td align="center">${user.userName}</td>
                            <td align="center">${user.role}</td>
                            <td align="center">
                                <button type="button" data-id="${user.userId}" class="editUserBtn btn btn-info float-end">
                                    Edit
                                </button>

                                <button type="button" data-id="${user.userId}" class="deleteUserBtn btn btn-danger float-end">
                                    Delete
                                </button>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <div class="card-footer" align="center">
                <h4>
                    <a href="http://localhost:8080/admin/courses">
                        <button type="button" class="btn btn-secondary"> View Courses </button>
                    </a>
                </h4>
            </div>
        </div>
    </div>
</div>
<script>
$(document).on('click', '.deleteUserBtn', function (event) {
    event.preventDefault();

    if(confirm('Are you sure you want to delete this User?')) {
        var id = $(this).data('id');

        $.ajax( {
            type: "POST",
            url: "/admin/delete-user?userId=" + id,
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

 $(document).on('click', '.addUserBtn', function () {
    $('#addUserModal').modal('show');
 } );

 $(document).on('submit', '#add_user', function (event) {
     event.preventDefault();

     var formData = $(this).serialize(); // Serialize the form data

     $.ajax( {
         type: "POST",
         url: "/admin/add-user",
         data: formData,
         processData: false,
         success: function (response) {
             if(response.status === 500) {
                 $('#errormessage').removeClass('d-none');
                 $('#errormessage').text(response.message);
              }
             else if(response.status === 200) {
                 $('#errormessage').addClass('d-none');
                 $('#addUserModal').modal('hide');
                 $('#add_user')[0].reset();
                 location.reload();
              }
          }
     } );

  } );

  $(document).on('click', '.editUserBtn', function () {
       var userId = $(this).data('id');
       $.ajax( {
           type: "GET",
           url: "/admin/edit-user?userId=" + userId,
           success: function (response) {
                $('#user_name').val(response.userName);
                $('#userId').val(response.userId);
                $('#user_role').val(response.role);

                $('#editUserModal').modal('show');
                }
       } );

    } );

$(document).on('submit', '#update_user', function (event) {
     event.preventDefault();

     var formData = $(this).serialize(); // Serialize the form data

     $.ajax( {
         type: "POST",
         url: "/admin/update-user",
         data: formData,
         processData: false,
         success: function (response) {
             if(response.status === 500) {
                 $('#errormessage').removeClass('d-none');
                 $('#errormessage').text(response.message);
              }
             else if(response.status === 200) {
                 $('#errormessage').addClass('d-none');
                 $('#editUserModal').modal('hide');
                 $('#update_user')[0].reset();
                 location.reload();
              }
          }
     } );

  } );
</script>
<%@ include file="/WEB-INF/views/inc/footer.jsp" %>