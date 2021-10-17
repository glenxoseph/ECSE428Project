<template>
  <div id="Question">
    <h2>Question 1</h2>
    <h3>Where is Trottier?</h3>
    <table>
      <tr>
            <button>A. 845 Rue Sherbrooke O</button>
      </tr>
      <tr>
            <button>B. 3630 Rue University</button>
      </tr>
      <tr>
            <button>C. 859 Sherbrooke St W</button>
      </tr>
      <tr>
              <input type="text" placeholder="Others...">
      </tr>
    </table>
    <p>
      <button>Submit</button>
    </p>
    <p>
      <button>Go Back</button>
    </p>
    <p>
      <span style="color:red">Error: Sample Error Message</span>
    </p>
  </div>
</template>
<script>
</script>
<style>
  #Question {
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
</style>