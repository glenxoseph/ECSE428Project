<template>

  <div>
    <router-link to="/settings"><b-button class="p3" type="submit" size="sm" variant="dark">Back to Settings</b-button></router-link>

    <hr style="width: 50%">

    <h1> Settings </h1>

    <h4 class="m-5"> Delete Account </h4>

    <form class="m-5">
      <label for="deleteAccount">Delete Account</label>
      <div class="mb-2 d-flex justify-content-center">
        <b-alert show variant="warning">By clicking the button, you give your consent to delete the account.</b-alert>
      </div>
       <label for="currentEmail">Current Email</label>
      <div class="mb-2 d-flex justify-content-center">
        <b-form-input type="email" class="form-control" style="width: 40%;" id="currentEmail" placeholder="Current Email" />
      </div>
      <label for="password">Password</label>
      <div class="mb-2 d-flex justify-content-center">
        <b-form-input v-model="inputPassword" type="password" class="form-control" style="width: 40%;" id="password" placeholder="Password"/>
      </div>

      <div class="m-5">
        <b-button class="p3" type="submit" size="lg" variant="danger" v-on:click="deleteAccount">Confirm</b-button>
      </div>
    </form>

  </div>

</template>

<script>
  import axios from 'axios';
  export default {
    data() {
      return {
        currentEmail: '',
        password: '',
        errors: []
      }
    },

    methods: {
      deleteAccount() {
        console.log("Email:" + currentEmail.value  + " password:" + password.value);

        axios.get('http://localhost:8081/deleteAccount/' + currentEmail.value +'?password=' + password.value )
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

