<template>

  <div>
    <router-link to="/home"><b-button class="p3" type="submit" size="sm" variant="dark">Back to Home</b-button></router-link>

    <hr style="width: 50%">

    <h1 id="title"> Match History </h1>

    <b-container>
      <b-row align-h="center">
        <b-col cols="6">
          <div class="leaderboardTable">
            <table class="table table-striped table-bordered">
              <thead>
              <tr>
                <th> Quiz Name </th>
                <th> Score </th>
              </tr>
              </thead>

              <tbody>
              <tr v-for="entry in leaderboardEntries">
                <td>{{ entry.quizName }}</td>
                <td>{{ entry.quizScore }}</td>
              </tr>
              </tbody>
            </table>
          </div>
        </b-col>
      </b-row>
    </b-container>

  </div>

</template>

<script>
import axios from "axios";

export default {
  name: "MatchHistory",
  data() {
    return {
      leaderboardEntries: ''
    }
  },
  methods: {
    getLeaderboard(accountEmail) {
      axios.get("http://localhost:8081/leaderboard/entries/" + accountEmail)
        .then(response => {
          console.log(response.data)
          this.leaderboardEntries = response.data
        })
        .catch(error => {
          console.log(error.response)
        })
    }
  },
  created() {
    this.getLeaderboard(localStorage.getItem('username'))
  }
}


</script>

<style>

#title {
  padding-bottom: 40px;
}

</style>
