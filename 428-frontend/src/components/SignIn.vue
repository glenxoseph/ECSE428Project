<template>

  <div>
    <h1> Welcome to GameName </h1>

    <h4 class="m-5"> Sign In </h4>

    <form class="m-5">
      <label for="inputUsername">Email</label>
      <div class="mb-2 d-flex justify-content-center">
        <b-form-input v-model="inputUsername" type="email" class="form-control" style="width: 40%;" id="inputUsername" placeholder="Email"/>
      </div>
      <label for="inputPassword">Password</label>
      <div class="mb-2 d-flex justify-content-center">
        <b-form-input v-model="inputPassword" type="password" class="form-control" style="width: 40%;" id="inputPassword" placeholder="Password"/>
      </div>
      <div class="m-3">
       <b-button class="p3" type="submit" size="lg" variant="dark" v-on:click="getLogin">Sign In</b-button>
      </div>
    </form>

    <label for="createAccount">Don't Have An Account?</label>
    <div class="d-flex justify-content-center">
      <b-button size="sm" variant="dark" id="createAccount">Create An Account</b-button>
    </div>
  
  </div>

</template>

<script>
  import axios from 'axios';
  export default {
    data() {
      return {
        inputUsername: '',
        inputPassword: '',
        errors: []
      }
    },

    methods: {
      getLogin() {
        console.log("username:" + inputUsername.value + " password:" + inputPassword.value);
        localStorage.setItem("username", inputUsername.value);
        axios.get('http://localhost:8081/login' + '?email=' + inputUsername.value + "&password=" + inputPassword.value)
        .then(response => {
          if (response.status == 200) {
            this.$router.push('/home')
          }
        })
        .catch(e => {
          this.errors.push(e)
        })
      }
    }
  }

</script>


