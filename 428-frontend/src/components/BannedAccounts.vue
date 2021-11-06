<template>

  <div>
    <router-link to="/settings"><b-button class="p3" type="submit" size="sm" variant="dark">Back to Settings</b-button></router-link>

    <hr style="width: 50%">

    <h1> Settings </h1>

    <h4 class="m-4"> Banned Accounts </h4>

    <h5 class="m-2"> Ban Account </h5>

    <form class="m-3">
      <label for="banAccountEmail">Account Email</label>
      <div class="mb-2 d-flex justify-content-center">
        <b-form-input type="email" class="form-control" style="width: 40%;" id="banAccountEmail" placeholder="Account Email"/>
      </div>
      <div class="m-3">
       <router-link to="/home"><b-button class="p3" type="submit" size="md" variant="dark">Confirm</b-button></router-link>
      </div>
    </form>

    <hr style="width: 50%">

    <h5 class="m-2"> Unban Account </h5>

    <form class="m-3">
      <label for="unbanAccountEmail">Account Email</label>
      <div class="mb-2 d-flex justify-content-center">
        <b-form-input type="email" class="form-control" style="width: 40%;" id="unbanAccountEmail" placeholder="Account Email"/>
      </div>
      <div class="m-3">
       <router-link to="/home"><b-button class="p3" type="submit" size="md" variant="dark">Confirm</b-button></router-link>
      </div>
    </form>

  </div>

</template>


