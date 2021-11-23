<template>

  <div>
    <router-link to="/settings"><b-button class="p3" type="submit" size="sm" variant="dark">Back to Settings</b-button></router-link>

    <hr style="width: 50%">

    <h1> Settings </h1>

    <h4 class="m-5"> Change Password </h4>

    <form class="m-5">
      <label>Current Password</label>
      <div class="mb-2 d-flex justify-content-center">
        <b-form-input v-model="currentPassword" type="password" class="form-control" style="width: 40%;" id="currentPassword" placeholder="Current Password"/>
      </div>
      <label>New Password</label>
      <div class="mb-2 d-flex justify-content-center">
        <b-form-input v-model="newPassword" type="password" class="form-control" style="width: 40%;" id="newPassword" placeholder="New Password"/>
      </div>
      <label>Confirm Password</label>
      <div class="mb-2 d-flex justify-content-center">
        <b-form-input v-model="confirmPassword" type="password" class="form-control" style="width: 40%;" id="confirmPassword" placeholder="Confirm Password"/>
      </div>
      <div class="m-5">
       <b-button class="p3" type="submit" size="lg" variant="dark"
                 :disabled="!currentPassword || !newPassword || !confirmPassword"
                 @click="changePassword(currentPassword, newPassword, confirmPassword)">
         Confirm
       </b-button>
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
      currentPassword: '',
      newPassword: '',
      confirmPassword: '',
      errorMessage: '',
      errorMessageVisibility: '',
      successMessage: '',
      successMessageVisibility: ''
    }
  },
  methods: {
    changePassword(currentPassword, newPassword, confirmPassword) {
      if(currentPassword == newPassword) {
        this.errorMessageVisibility = true
        this.errorMessage = "The new password cannot be the same as the old password."
        setTimeout(() => this.errorMessageVisibility = false, 3000)
        this.clearFields()
      } else if (!(newPassword == confirmPassword)) {
        this.errorMessageVisibility = true
        this.errorMessage = "The confirmed password does not match the new password."
        setTimeout(() => this.errorMessageVisibility = false, 3000)
        this.confirmPassword = ''
      } else {
        axios.post("http://localhost:8081/users/" + localStorage.getItem("username") + "/changePassword", {
          "oldPassword": currentPassword,
          "newPassword": newPassword
        })
        .then(response => {
          console.log(response.data)
          this.successMessageVisibility = true
          this.successMessage = "Password changed successfully!"
          this.clearFields()
          setTimeout(() => this.successMessageVisibility = false, 3000)
        })
        .catch(error => {
          console.log(error.response.data)
          this.errorMessageVisibility = true
          this.errorMessage = error.response.data.message
          this.clearFields()
          setTimeout(() => this.errorMessageVisibility = false, 3000)
        })
      }
    },
    clearFields() {
      this.currentPassword = ''
      this.newPassword = ''
      this.confirmPassword = ''
    }
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


