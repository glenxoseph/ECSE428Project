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
       <b-button class="p3" type="submit" size="md" variant="dark" v-on:click="banAccount">Confirm</b-button>
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
       <b-button class="p3" type="submit" size="md" variant="dark" v-on:click="unbanAccount">Confirm</b-button>
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
        errors: [],
        errorMessage: '',
        errorMessageVisibility: false,
        successMessage: '',
        successMessageVisibility: false
      }
    },

    methods: {
      banAccount() {
        console.log("banning email... " + banAccountEmail.value);
        const body = {
            email: "admin@mail.com" ,
            password: "password"
        };
        axios.post('http://localhost:8081/configs/ban/' + banAccountEmail.value, body)
        .then(response => {
          console.log(banAccountEmail.value + " successfully banned.")
          console.log(response.data)
          this.successMessageVisibility = true
          this.successMessage = response.data.message
          setTimeout(() => this.successMessageVisibility = false, 4000)
        })
        .catch(error => {
          console.log(error.response.data)
          this.errorMessageVisibility = true
          this.errorMessage = error.response.data.message
          setTimeout(() => this.errorMessageVisibility = false, 4000)
          this.banAccountEmail = ''
        })
      },
      unbanAccount() {
        console.log("unbanning email... " + unbanAccountEmail.value);
        const body = {
            email: "admin@mail.com" ,
            password: "password"
        };
        axios.post('http://localhost:8081/configs/unban/' + unbanAccountEmail.value, body)
        .then(response => {
          console.log(unbanAccountEmail.value + " successfully unbanned.");
        })
        .catch(e => {
          this.errors.push(e)
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
