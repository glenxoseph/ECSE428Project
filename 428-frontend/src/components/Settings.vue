<template>

  <div>
    <h1> Settings </h1>

    <h4 class="m-5"> Change Password </h4>

    <form class="m-5">
      <label for="currentPassword">Current Password</label>
      <div class="mb-2 d-flex justify-content-center">
        <b-form-input type="password" class="form-control" style="width: 40%;" id="currentPassword" placeholder="Current Password"/>
      </div>
      <label for="newPassword">New Password</label>
      <div class="mb-2 d-flex justify-content-center">
        <b-form-input type="password" class="form-control" style="width: 40%;" id="newPassword" placeholder="New Password"/>
      </div>
      <label for="confirmPassword">Confirm Password</label>
      <div class="mb-2 d-flex justify-content-center">
        <b-form-input type="password" class="form-control" style="width: 40%;" id="confirmPassword" placeholder="Confirm Password"/>
      </div>
      <div class="m-5">
       <router-link to="/home"><b-button class="p3" type="submit" size="lg" variant="dark">Confirm</b-button></router-link>
      </div>
    </form>
  
  </div>

</template>


