<div class="modal fade" id="markStudentModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title">Mark Student</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <form id="mark_student">
        <div class="modal-body">
            <div class="alert alert-warning d-none" id="errormessage"></div>
            <div class="input-group mb-3">
              <div class="input-group-prepend">
                  <label class="input-group-text">First</label>
              </div>
              <input type="text" required id="firstMark" name="firstMark" class="form-control">
              <input type="hidden" id="studentId" name="studentId">
              <input type="hidden" id="courseId" name="courseId">
            </div>

            <div class="input-group mb-3">
              <div class="input-group-prepend">
                  <label class="input-group-text">Second</label>
              </div>
              <input type="text" required id="secondMark" name="secondMark" class="form-control">
            </div>

            <div class="input-group mb-3">
              <div class="input-group-prepend">
                  <label class="input-group-text">Final</label>
              </div>
              <input type="text" required id="finalMark" name="finalMark" class="form-control">
            </div>

            <div class="input-group mb-3">
                    <div class="input-group-prepend">
                        <label class="input-group-text" for="inputGroupSelect01">Symbol</label>
                    </div>
                    <select required class="custom-select" name="symbol" id="symbol">
                            <option value="A">A</option>
                            <option value="B">B</option>
                            <option value="C">C</option>
                            <option value="D">D</option>
                            <option value="F">F</option>
                    </select>
            </div>
        </div>
        <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
            <button type="submit" name="submit" class="btn btn-primary" id="submit">Mark</button>
        </div>
      </form>
    </div>
  </div>
</div>