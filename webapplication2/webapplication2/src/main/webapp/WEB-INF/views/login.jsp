<%@ include file="/WEB-INF/views/inc/header.jsp" %>
     <div class="container">
                <div class="row">
                    <div class="col-8 mx-auto">
                        <h1 class="text-center my-5 py-3">Login</h1>
                        <p><font color="red">${errorMessage}</font></p>

                        <form class="p-5 border mb-5" method="POST" action="http://localhost:8080/auth/verify">
                            <div class="input-group mb-3">
                                <div class="input-group-prepend">
                                    <lable class="input-group-text">Userame</lable>
                                </div>
                                <input type="text" required name="userName" class="form-control" id="userName">
                            </div>

                            <div class="input-group mb-3">
                                <div class="input-group-prepend">
                                    <label class="input-group-text" for="inputGroupSelect01">Password</label>
                                </div>
                                <input type="password" required name="password" class="form-control" id="password">
                            </div>
                            <button type="submit" name="submit" class="btn btn-primary">Submit</button>
                        </form>

                    </div>
                </div>
            </div>
<%@ include file="/WEB-INF/views/inc/footer.jsp" %>