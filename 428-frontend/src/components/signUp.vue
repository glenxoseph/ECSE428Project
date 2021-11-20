<template>

  <div>
    <h1> Welcome to GameName </h1>

    <h4 class="m-5"> Sign Up </h4>

    <form class="m-5">
      <label for="inputUsername">Email</label>
      <div class="mb-2 d-flex justify-content-center">
        <b-form-input v-model="inputUsername" type="email" class="form-control" style="width: 40%;" id="inputUsername" placeholder="Email"/>
      </div>
      <label for="inputPassword">Password</label>
      <div class="mb-2 d-flex justify-content-center">
        <b-form-input v-model="inputPassword" type="password" class="form-control" style="width: 40%;" id="inputPassword" placeholder="Password"/>
      </div>
      <label for="retypePassword">Retype Password</label>
      <div class="mb-2 d-flex justify-content-center">
        <b-form-input v-model="inputPassword" type="password" class="form-control" style="width: 40%;" id="inputPassword" placeholder="Password"/>
      </div>
      <div class="m-3">
       <b-button class="p3" type="submit" size="lg" variant="dark" v-on:click="getLogin">Sign Up</b-button>
      </div>
    </form>

    <label for="createAccount">Already Have An Account?</label>
    <div class="d-flex justify-content-center">
      <b-button size="sm" variant="dark" id="createAccount">Sign In</b-button>
    </div>

  </div>

</template>