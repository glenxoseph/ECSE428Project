<template>

  <div>
    <router-link to="/settings"><b-button class="p3" type="submit" size="sm" variant="dark">Back to Settings</b-button></router-link>

    <hr style="width: 50%">

    <h1> Settings </h1>

    <h4 class="m-5"> Delete Account </h4>

    <form class="m-5">
      <div class="mb-2 d-flex justify-content-center">
        <b-alert show variant="warning">By clicking the button, you give your consent to delete the account.</b-alert>
      </div>
       <label>Current Email</label>
      <div class="mb-2 d-flex justify-content-center">
        <b-form-input v-model="email" type="email" class="form-control" style="width: 40%;" placeholder="Current Email" />
      </div>
      <label>Password</label>
      <div class="mb-2 d-flex justify-content-center">
        <b-form-input v-model="password" type="password" class="form-control" style="width: 40%;" placeholder="Password"/>
      </div>

      <div class="m-5">
        <b-button class="p3" type="submit" size="lg" variant="danger"
                  :disabled="!email || !password"
                  v-on:click="deleteAccount(email, password)">Confirm</b-button>
      </div>
    </form>

    <p class="errorMessage" v-show="errorMessageVisibility">{{errorMessage}}</p>
    <p class="successMessage" v-show="successMessageVisibility">{{successMessage}}</p>

  </div>

</template>

<script>
  import axios from 'axios';
  export default {
    data() {
      return {
        email: '',
        password: '',
        errors: [],
        errorMessage: '',
        errorMessageVisibility: false,
        successMessage: '',
        successMessageVisibility: false,
      }
    },

    methods: {
      deleteAccount: function(email, password) {
        if(!(email == localStorage.getItem("username"))) {
          this.errorMessageVisibility = true
          this.errorMessage = "This email address is not associated to this account."
          setTimeout(() => this.errorMessageVisibility = false, 4000)
          this.email = ''
          this.password = ''
        } else {
        axios.delete('http://localhost:8081/deleteAccount/' + email, {
          params: {
            password: password
          }
        })
          .then(response => {
            console.log(response.data)
            localStorage.removeItem("username")
            this.successMessageVisibility = true
            this.successMessage = "Your account has been deleted successfully, you will be redirected to the login page."
            setTimeout(() => this.successMessageVisibility = false + this.$router.push('/'), 4000)
          })
          .catch(error => {
            console.log(error.response.data)
            this.errorMessageVisibility = true
            this.errorMessage = error.response.data.message
            setTimeout(() => this.errorMessageVisibility = false, 4000)

            if (this.errorMessage.indexOf('Password') > -1) {
              this.password = ''
            }
          })
      }
      }
    },
    created() {
      console.log(localStorage.getItem("username"))
    }
  }

</script>

<style>

.errorMessage {
  color: darkred;
}

.successMessage {
  color: green;
}

</style>

