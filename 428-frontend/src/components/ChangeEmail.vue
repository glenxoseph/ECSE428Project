<template>

  <div>
    <router-link to="/settings"><b-button class="p3" type="submit" size="sm" variant="dark">Back to Settings</b-button></router-link>

    <hr style="width: 50%">

    <h1> Settings </h1>

    <h4 class="m-5"> Change Email </h4>

    <form class="m-5">
      <label>Current Email</label>
      <div class="mb-2 d-flex justify-content-center">
        <b-form-input v-model="currentEmail" type="email" class="form-control" style="width: 40%;" placeholder="Enter current email address" />
      </div>
      <label>New Email</label>
      <div class="mb-2 d-flex justify-content-center">
        <b-form-input v-model="newEmail" type="email" class="form-control" style="width: 40%;" placeholder="Enter new email address" />
      </div>
      <label>Password</label>
      <div class="mb-2 d-flex justify-content-center">
        <b-form-input v-model="password" type="password" class="form-control" style="width: 40%;" placeholder="Enter your password" />
      </div>
      <div class="m-5">
        <b-button class="p3" type="submit" size="lg" variant="dark"
                  :disabled="!currentEmail || !newEmail || !password"
                  v-on:click="changeEmail(currentEmail, newEmail, password)">Confirm</b-button>
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
        currentEmail: '',
        newEmail: '',
        password: '',
        errors: [],
        errorMessage: '',
        errorMessageVisibility: false,
        successMessage: '',
        successMessageVisibility: false
      }
    },

    methods: {
      changeEmail: function(currentEmail, newEmail, password) {
        if (currentEmail == newEmail) {
          this.errorMessageVisibility = true
          this.errorMessage = "The new email cannot be the same as the old one."
          this.newEmail = ''
          setTimeout(() => this.errorMessageVisibility = false, 4000)
        } else {
          axios.put('http://localhost:8081/account/changeEmail', null, {
            params: {
              "oldEmail": currentEmail,
              "newEmail": newEmail,
              "password": password
            }
          })
            .then(response => {
              console.log(response.data)
              this.successMessageVisibility = true
              this.successMessage = "Your email address has been changed successfully!"
              this.currentEmail = ''
              this.newEmail = ''
              this.password = ''
              setTimeout(() => this.successMessageVisibility = false, 4000)
              localStorage.setItem("username", newEmail)
            })
            .catch(error => {
              console.log(error.response)
              this.errorMessageVisibility = true
              this.errorMessage = error.response.data.message
              setTimeout(() => this.errorMessageVisibility = false, 4000)

              if (this.errorMessage.indexOf('password') > -1) {
                this.password = ''
              }

              if (this.errorMessage.indexOf('email') > -1) {
                this.newEmail = ''
              }

              if (this.errorMessage.indexOf('account') > -1) {
                this.currentEmail = ''
                this.newEmail = ''
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
