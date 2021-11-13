<template>

  <div>
    <router-link to="/settings"><b-button class="p3" type="submit" size="sm" variant="dark">Back to Settings</b-button></router-link>

    <hr style="width: 50%">

    <h1> Settings </h1>

    <h4 class="m-4"> Banned Accounts </h4>

    <h5 class="m-2"> Ban Account </h5>

    <form class="m-3">
      <label>Account Email</label>
      <div class="mb-2 d-flex justify-content-center">
        <b-form-input v-model="banAccountEmail" type="email" class="form-control" style="width: 40%;" id="banAccountEmail" placeholder="Account Email"/>
      </div>
      <div class="m-3">
       <b-button class="p3" type="submit" size="md" variant="dark" v-on:click="banAccount(banAccountEmail)">Confirm</b-button>
      </div>
    </form>

    <hr style="width: 50%">

    <h5 class="m-2"> Unban Account </h5>

    <form class="m-3">
      <label>Account Email</label>
      <div class="mb-2 d-flex justify-content-center">
        <b-form-input v-model="unbanAccountEmail" type="email" class="form-control" style="width: 40%;" id="unbanAccountEmail" placeholder="Account Email"/>
      </div>
      <div class="m-3">
       <b-button class="p3" type="submit" size="md" variant="dark" v-on:click="unbanAccount(unbanAccountEmail)">Confirm</b-button>
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
        banAccountEmail: '',
        unbanAccountEmail: '',
        errors: [],
        errorMessage: '',
        errorMessageVisibility: false,
        successMessage: '',
        successMessageVisibility: false
      }
    },

    methods: {
      banAccount(banAccountEmail) {
        console.log("banning email... " + banAccountEmail);
        const body = {
            email: "admin@mail.com" ,
            password: "password"
        };
        axios.post('http://localhost:8081/configs/ban/' + banAccountEmail, body)
        .then(response => {
          console.log(banAccountEmail + " successfully banned.")
          console.log(response.data)
          this.successMessageVisibility = true
          this.successMessage = banAccountEmail + " successfully banned."
          setTimeout(() => this.successMessageVisibility = false, 4000)
          this.banAccountEmail = ''
        })
        .catch(error => {
          console.log(error.response.data)
          this.errorMessageVisibility = true
          this.errorMessage = error.response.data.message
          setTimeout(() => this.errorMessageVisibility = false, 4000)
          this.banAccountEmail = ''
        })
      },
      unbanAccount(unbanAccountEmail) {
        console.log("unbanning email... " + unbanAccountEmail);
        const body = {
            email: "admin@mail.com" ,
            password: "password"
        };
        axios.post('http://localhost:8081/configs/unban/' + unbanAccountEmail, body)
        .then(response => {
          console.log(unbanAccountEmail + " successfully unbanned.");
          console.log(response.data)
          this.successMessageVisibility = true
          this.successMessage = unbanAccountEmail + " successfully unbanned."
          setTimeout(() => this.successMessageVisibility = false, 4000)
          this.unbanAccountEmail = ''
        })
        .catch(error => {
          console.log(error.response.data)
          this.errorMessageVisibility = true
          this.errorMessage = error.response.data.message
          setTimeout(() => this.errorMessageVisibility = false, 4000)
          this.unbanAccountEmail = ''
        })
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
