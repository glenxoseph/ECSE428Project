<template>
  <div>
    <h3 class="title"> Create an Account </h3>

    <form class="m-4">
      <label>Name</label>
      <div class="mb-2 d-flex justify-content-center textField">
        <b-form-input v-model="name" class="form-control" style="width: 40%;" placeholder="Enter your name"/>
      </div>

      <label>Email</label>
      <div class="mb-2 d-flex justify-content-center textField">
        <b-form-input v-model="email" type="email" class="form-control" style="width: 40%;" placeholder="Enter your email address"/>
      </div>

      <label>Password</label>
      <div class="mb-2 d-flex justify-content-center textField">
        <b-form-input v-model="password" type="password" class="form-control" style="width: 40%;" placeholder="Enter your password"/>
      </div>

      <label>Verify Password</label>
      <div class="mb-2 d-flex justify-content-center textField">
        <b-form-input v-model="passwordVerification" type="password" class="form-control" style="width: 40%;" placeholder="Re-enter your password"/>
      </div>
      <div class="m-3">
        <b-button class="p3" type="submit" size="lg" variant="dark"
                  :disabled="!name || !email || !password || !passwordVerification"
                  v-on:click="createAccount(email, name, password, passwordVerification)">Create Account</b-button>
      </div>
    </form>

    <label for="createAccount">Already Have An Account?</label>
    <div class="d-flex justify-content-center">
      <b-button size="sm" variant="dark" id="createAccount" v-on:click="goToLogin">Back to Login</b-button>
    </div>

    <p class="errorMessage" v-show="errorMessageVisibility">{{errorMessage}}</p>

  </div>
</template>

<script>
import axios from "axios";
export default {
  name: "CreateAccount",
  data() {
    return {
      name: '',
      email: '',
      password: '',
      passwordVerification: '',
      errorMessage: '',
      errorMessageVisibility: false
    }
  },
  methods: {
    goToLogin() {
      this.$router.push('/')
    },
    createAccount: function(email, name, password, passwordVerification) {
      if (!(password == passwordVerification)) {
        this.errorMessageVisibility = true
        this.errorMessage = "The passwords do not match."
        this.passwordVerification = ''
        setTimeout(() => this.errorMessageVisibility = false, 3000)
      } else {
        axios.post('http://localhost:8081/createAccount', {
          email: email,
          name: name,
          password: password
        })
        .then(response => {
          console.log(response.data)
          axios.get('http://localhost:8081/login',{ params:{
              email: email,
              password: password }
          })
          .then(response => {
            console.log(response.data)
            console.log(localStorage.getItem("username"))
            this.$router.push('/home')
          })
          .catch(error => {
            console.log(error.data)
          })
        })
        .catch(error => {
          console.log(error.response.data)
          this.errorMessageVisibility = true
          this.errorMessage = error.response.data.message
          this.email = ''
          this.password = ''
          this.passwordVerification = ''
          setTimeout(() => this.errorMessageVisibility = false, 3000)
        })
      }
    }
  }
}
</script>

<style scoped>

.title {
  padding-bottom: 20px;
}

.textField {
  padding-bottom: 12px;
}

.errorMessage {
  padding-top: 20px;
  color: darkred;
}

</style>
