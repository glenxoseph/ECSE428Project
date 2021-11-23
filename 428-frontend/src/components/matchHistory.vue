<template>

  <div>
    <router-link to="/home"><b-button class="p3" type="submit" size="sm" variant="dark">Back to Home</b-button></router-link>

    <hr style="width: 50%">

    <h1> View Match History </h1>

    <form class="m-5">
      <table id='histroy_table'>
        <tr>
          <th>Match Id</th>
          <th>Game Mode</th>
          <th>Winner</th>
        </tr>
        <tr>
          <td>1</td>
          <td>Solo</td>
          <td>You</td>
        </tr>
        <tr>
          <td>2</td>
          <td>Online</td>
          <td>User: John</td>
        </tr>
        <tr>
          <td>3</td>
          <td>Online</td>
          <td>User: Kenny</td>
        </tr>
        <tr>
          <td>4</td>
          <td>Online</td>
          <td>You</td>
        </tr>
      </table>
    </form>

  </div>

</template>

<style>
  #history_table {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    color: #2c3e50;
    background: #f2ece8;
  }
  tr {
    text-align: left;
  }
  table {
    margin-left: auto;
    margin-right: auto;
  }

  table {
    border-collapse: separate;
    border-spacing: 50px 0;
  }

  td {
    padding: 10px 0;
  }
</style>
