<template>

  <div>
    <h1> Welcome Sean! </h1>

    <h4 class="m-5"> Please Select a Game Mode </h4>

    <div class="m-5">
        <b-button type="submit" size="lg" variant="primary">Solo Mode</b-button>
    </div>
    <div class="m-5">
        <b-button type="submit" size="lg" variant="primary">Multiplayer Mode</b-button>
    </div>

    <hr style="width: 50%">

    <div class="m-5">
        <b-button class="p3" type="submit" size="lg" variant="dark">My Statistics</b-button>
    </div>

     <hr style="width: 50%">

    <div class="d-inline justify-content-center m-2">
      <router-link to="/settings"><b-button size="sm" variant="dark" id="settings">Settings</b-button></router-link>
    </div>
    <div class="d-inline justify-content-center m-2">
      <b-button size="sm" variant="danger" id="logout" v-on:click="postLogout">Logout</b-button>
    </div>

  
  </div>

</template>

<script>
  import axios from 'axios';

  export default {
    data() {
      return {
        errors: []
      }
    },

    methods: {
      postLogout() {
        axios.post('http://localhost:8081/logout' + '?email=' + localStorage.getItem('username'))
        .then(response => {
          if (response.status == 200) {
            this.$router.push('/').catch(()=>{});
          }
        })
        .catch(e => {
          this.errors.push(e)
        })
      }
    }
  }

</script>

