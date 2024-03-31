<div class="modal fade" id="editCourseModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title">Edite Course</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <form id="update_course">
        <div class="modal-body">
            <div class="alert alert-warning d-none" id="errormessage"></div>
            <div class="input-group mb-3">
              <div class="input-group-prepend">
                  <label class="input-group-text">Name</label>
              </div>
              <input type="text" required id="course_name" name="courseName" class="form-control">
              <input type="hidden" id="courseId" name="courseId">
            </div>
        </div>
        <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
            <button type="submit" name="submit" class="btn btn-primary" id="submit">Update</button>
        </div>
      </form>
    </div>
  </div>
</div>