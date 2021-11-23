<template>
  <div>

    <h1 id="title"> Quiz Selection </h1>

    <div>
      <b-container class="bv-example-row">
        <b-row>
          <b-col>
            <h5>Select a Quiz</h5>
          </b-col>
          <b-col>
              <b-select v-model="selectedQuiz">
                <option v-for="quizItem in quizList" :value="quizItem">{{quizItem}}</option>
              </b-select>
          </b-col>
          <b-col>
            <b-button variant="success" :disabled="!selectedQuiz" @click="goToQuizPage(selectedQuiz)">Start Quiz</b-button>
          </b-col>
        </b-row>
      </b-container>
    </div>

    <div>
      <table class="table table-striped">
        <thead>
          <tr>
            <th> Quiz Name </th>
            <th> Quiz Topic </th>
            <th> Number of Questions </th>
          </tr>
        </thead>

        <tbody>
          <tr class="clickableRow" v-for="quiz in quizzes">
            <td>{{ quiz.name }}</td>
            <td>{{ quiz.topic }}</td>
            <td>{{ quiz.nbQuestion }}</td>
          </tr>
        </tbody>
      </table>

      <p class="errorMessage" v-show="errorMessageVisibility">{{errorMessage}}</p>

    </div>
  </div>
</template>

<script>
import axios from "axios";
export default {
  name: "QuizSelectionPage",
  data() {
    return {
      fields: ['quizName', 'topic', 'number of questions'],
      quizzes: '',
      quizName: '',
      quizTopic: '',
      nbQuestions: '',
      selected: [],
      quizList:'',
      selectedQuiz: '',
      errorMessage: '',
      errorMessageVisibility: false
    }
  },
   created() {
     this.getQuizzes()
   },
  methods: {
    getQuizzes: function() {
      axios.get("http://localhost:8081/quiz/all", null)
        .then(response => {
          let quizzes = response.data.map(function(quiz) {
            return quiz.name
          })
          console.log(response.data)
          this.quizList = quizzes
          this.quizzes = response.data
        })
        .catch(error => {
          console.log(error.response.data)
          this.errorMessageVisibility = true
          this.errorMessage = "Could not load quizzes. You will be redirected to the home page."
          setTimeout(() => this.errorMessageVisibility = false + this.$router.push('/home'), 4000)

        })
    },
    goToQuizPage(quiz) {
      axios.get("http://localhost:8081/quiz/" + quiz, null)
        .then(response => {
          console.log(response.data)
          //this.$router.push('/quizPage')
        })
        .catch(error => {
          console.log(error)
          this.errorMessageVisibility = true
          this.errorMessage = error.response
          setTimeout(() => this.errorMessageVisibility = false, 4000)
        })
    }
  }
}
</script>

<style scoped>

.bv-example-row {
  padding: 30px;
}

.errorMessage {
  color: darkred;
}

</style>
