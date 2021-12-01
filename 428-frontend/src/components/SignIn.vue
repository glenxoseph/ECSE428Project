<template>

  <div>
    <h1> Welcome to Full Moon Trivia </h1>

    <h4 class="m-5"> Sign In </h4>

    <form class="m-5">
      <label>Email</label>
      <div class="mb-2 d-flex justify-content-center">
        <b-form-input v-model="inputUsername" type="email" class="form-control" style="width: 40%;" id="inputUsername" placeholder="Email"/>
      </div>
      <label>Password</label>
      <div class="mb-2 d-flex justify-content-center">
        <b-form-input v-model="inputPassword" type="password" class="form-control" style="width: 40%;" id="inputPassword" placeholder="Password"/>
      </div>
      <div class="m-3">
       <b-button class="p3" type="submit" size="lg" variant="dark"
                 :disabled="!inputUsername || !inputPassword"
                 v-on:click="getLogin">Sign In</b-button>
      </div>
    </form>

    <label for="createAccount">Don't Have An Account?</label>
    <div class="d-flex justify-content-center">
      <b-button size="sm" variant="dark" id="createAccount" v-on:click="goToCreateAccountPage">Create An Account</b-button>
    </div>

    <p class="errorMessage" v-show="errorMessageVisibility">{{ errorMessage }}</p>

  </div>

</template>

<script>
  import axios from 'axios';
  export default {
    data() {
      return {
        inputUsername: '',
        inputPassword: '',
        errors: [],
        errorMessage: '',
        errorMessageVisibility: false
      }
    },

    methods: {
      getLogin() {
        localStorage.setItem("username", inputUsername.value);
        axios.get('http://localhost:8081/login' + '?email=' + inputUsername.value + "&password=" + inputPassword.value)
        .then(response => {
          console.log(response.data)
          this.$router.push('/home')
        })
        .catch(error => {
          console.log(error.response.data.message)
          this.errors.push(error)
          this.errorMessageVisibility = true
          this.errorMessage = error.response.data.message
          setTimeout(() => this.errorMessageVisibility = false, 4000)

          if(error.response.data.message.indexOf('Password') > -1) {
            this.inputPassword = ''
          } else {
            this.inputPassword = ''
            this.inputUsername = ''
          }
        })
      },
      goToCreateAccountPage() {
        this.$router.push('/createAccount')
      },
      logoutAllAccounts() {
        axios.put("http://localhost:8081/logout/all",null,null)
          .then(() => {
            localStorage.clear()
          })
          .catch(error => {
            console.log(error.response)
          })
      }
    },
    created() {
      this.logoutAllAccounts()
    }
  }

</script>

<style>

.errorMessage {
  padding-top: 20px;
  color: darkred;
}

</style>

