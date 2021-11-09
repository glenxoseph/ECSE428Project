<template>

  <div>
    <router-link to="/settings"><b-button class="p3" type="submit" size="sm" variant="dark">Back to Settings</b-button></router-link>

    <hr style="width: 50%">

    <h1> Settings </h1>

    <h4 class="m-5"> Change Email </h4>

    <form class="m-5">
      <label for="currentEmail">Current Email</label>
      <div class="mb-2 d-flex justify-content-center">
        <b-form-input type="email" class="form-control" style="width: 40%;" id="currentEmail" placeholder="Current Email" />
      </div>
      <label for="newEmail">New Email</label>
      <div class="mb-2 d-flex justify-content-center">
        <b-form-input type="email" class="form-control" style="width: 40%;" id="newEmail" placeholder="New Email" />
      </div>
      <label for="enterPassword">Password</label>
      <div class="mb-2 d-flex justify-content-center">
        <b-form-input  v-model="inputPassword" type="password" class="form-control" style="width: 40%;" id="enterPassword" placeholder="Enter your password" />
      </div>
      <div class="m-5">
        <b-button class="p3" type="submit" size="lg" variant="dark"  v-on:click="changeEmail">Confirm</b-button>
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
        newEmail: '',
        enterPassword: '',
        errors: []
      }
    },

    methods: {
      changeEmail() {
        console.log("oldEmail:" + currentEmail.value + "newEmail" + newEmail.value + " password:" + enterPassword.value);
        axios.get('http://localhost:8081/account/changeEmail' + '?oldEmail=' + currentEmail.value + "&newEmail=" + newEmail.value + "&password=" + enterPassword.value)
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
