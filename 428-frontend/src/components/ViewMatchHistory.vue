<template>

  <div>
    <h1> Welcome To your user statistics, Sean! </h1>
    <div class="container">
        <div class="row">
        <div class="col-md-8 col-md-offset-2">
            <div class="panel panel-default">
            <div class="panel-heading">List of users</div>
            <div class="panel-body">
                <table class="table">
                <thead>
                    <tr>
                    <th>Games Won</th>
                    <th>Games Lost</th>
                    <th></th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="match in data" :key="match.id">
                    <td>Match number: {{ match.id }}</td>
                    <td>Match number: {{ match.id }}</td>
                    </tr>
                </tbody>
                </table>
            </div>
            </div>
        </div>
        </div>
  </div>

</template>

<script>
  import axios from 'axios';
  export default {
    data() {
      return {
        errors: [],
        data: []
      }
    },
    methods: {
      getAllMatches() {
        axios.get('http://localhost:8081/history')
        .then(response => {
            this.data = response
            print(this.data)
            for(match in this.data){
                
            }
        })
        .catch(e => {
          this.errors.push(e)
        })
      }
    }
  }
</script>
