<template>

  <div>
    <h1> Welcome To your user statistics, Sean! </h1>
    <div class="container">
        <div class="row d-flex justify-content-center">
        <div class="col-md-8 col-md">
            <div class="panel panel-default">
            <div class="panel-heading">List of Games played by Sean</div>
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
                    <tr>
                    <td>Match number: 00</td>
                    <td>Match number: 01</td>
                    </tr>
                    <tr>
                    <td>Match number: 02</td>
                    <td>Match number: 04</td>
                    </tr>
                    <tr>
                    <td>Match number: 03</td>
                    </tr>
                    <tr>
                    <td>Match number: 05</td>
                    </tr>
                </tbody>
                </table>
            </div>
            </div>
        </div>
        </div>
  </div>
 </div>
</template>
<style >

</style>
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
            console.log(this.data)
            console.log(this.data[0])
        })
        .catch(e => {
            console.log(e)
          this.errors.push(e)
        })
      }
    },
    created(){
        this.getAllMatches()
    }
  }
</script>
