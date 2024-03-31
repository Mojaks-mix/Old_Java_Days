<div class="modal fade" id="addUserModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title">Add User</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <form id="add_user">
        <div class="modal-body">
            <div class="alert alert-warning d-none" id="errormessage"></div>
            <div class="input-group mb-3">
              <div class="input-group-prepend">
                  <label class="input-group-text">Name</label>
              </div>
              <input type="text" required id="userName" name="userName" class="form-control">
            </div>
            <div class="input-group mb-3">
              <div class="input-group-prepend">
                  <label class="input-group-text">Password</label>
              </div>
              <input type="password" required id="pass" name="pass" class="form-control">
            </div>

            <div class="input-group mb-3">
              <div class="input-group-prepend">
                  <label class="input-group-text" for="inputGroupSelect01">Role</label>
              </div>
              <select required class="custom-select" name="userRole" id="userRole">
                <option value="student" selected>Student</option>
                <option value="teacher">Teacher</option>
                <option value="admin">Admin</option>
              </select>
            </div>
        </div>
        <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
            <button type="submit" name="submit" class="btn btn-primary" id="submit">Add</button>
        </div>
      </form>
    </div>
  </div>
</div>