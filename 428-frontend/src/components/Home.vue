<template>

  <div>
    <h1> Welcome {{accountName}}! </h1>

    <h4 class="m-5"> Please Select a Game Mode </h4>

    <div class="m-5">
        <b-button type="submit" size="lg" variant="primary" @click="goToQuizSelectionPage">Solo Mode</b-button>
    </div>
    <div class="m-5">
        <b-button v-b-modal.multiplayerModal type="submit" size="lg" variant="primary">Multiplayer Mode</b-button>

        <b-modal id="multiplayerModal" size="sm" centered hide-footer title="Multiplayer Game">
          <b-container fluid>
            <b-icon-hammer></b-icon-hammer>
            <h7 class="modalText">Feature coming soon!</h7>
            <b-icon-wrench></b-icon-wrench>
          </b-container>
        </b-modal>

    </div>

    <hr style="width: 50%">

    <div class="m-5">
        <b-button class="p3" type="submit" size="lg" variant="dark" @click="goToMatchHistoryPage">My Statistics</b-button>
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
        accountName: ''
      }
    },

    methods: {
      getAccount(email) {
        axios.get("http://localhost:8081/account/" + email)
          .then(response => {
            console.log(response.data)
            this.accountName = response.data.name.split(" ")[0]
          })
          .catch(error => {
            console.log(error.response)
          })
      },
      postLogout() {
        console.log(localStorage.getItem("username"))
        axios.post('http://localhost:8081/logout' + '?email=' + localStorage.getItem('username'))
        .then(response => {
          localStorage.removeItem("username")
          if (response.status == 200) {
            this.$router.push('/').catch(()=>{});
          }
        })
        .catch(e => {
          this.errors.push(e)
        })
      },
      goToQuizSelectionPage() {
        this.$router.push('/quizSelection')
      },
      goToMatchHistoryPage() {
        this.$router.push('/matchHistory')
      }
    },
    created() {
      console.log(localStorage.getItem("username"))
      this.getAccount(localStorage.getItem("username"))
    }
  }

</script>


<style>

.modalText{
  padding: 5px;
}

</style>

