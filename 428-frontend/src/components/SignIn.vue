<template>

  <div>
    <h1> Welcome to GameName </h1>

    <h4> Sign In </h4>

    <form class="m-5">
      <label for="inputUsername">Username</label>
      <div class="mb-2 d-flex justify-content-center">
        <b-form-input type="email" class="form-control" style="width: 40%;" id="inputUsername" placeholder="Username"/>
      </div>
      <label for="inputPassword">Password</label>
      <div class="mb-2 d-flex justify-content-center">
        <b-form-input type="password" class="form-control" style="width: 40%;" id="inputPassword" placeholder="Password"/>
      </div>
      <div class="m-3">
        <b-button class="p3" type="submit" size="lg" variant="dark">Sign In</b-button>
      </div>
    </form>

    <label for="createAccount">Don't Have An Account?</label>
    <div class="d-flex justify-content-center">
      <b-button size="sm" variant="dark" id="createAccount">Create An Account</b-button>
    </div>
  
  </div>

</template>


