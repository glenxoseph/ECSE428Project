<template>
  <div>
    <div>
        <router-link to="/quizSelection"><b-button class="p3" type="submit" size="sm" variant="dark">Back to Quiz Selection</b-button></router-link>
        <hr style="width: 50%">
      </div>

      <div>
      <h2>{{quizName}}</h2>
    </div>

    <div class="question">
      <h5>{{askedQuestion}}</h5>
    </div>
    <div class="buttons">
      <b-button variant="outline-dark" v-model="answer" size="lg" @click="answer = answer1">{{answer1}}</b-button>
      <b-button variant="outline-dark" v-model="answer" size="lg" @click="answer = answer2">{{answer2}}</b-button>
    </div>
    <div>
      <b-button variant="outline-dark" v-model="answer" size="lg" @click="answer = answer3">{{answer3}}</b-button>
      <b-button variant="outline-dark" v-model="answer" size="lg" @click="answer = answer4">{{answer4}}</b-button>
    </div>
    <div class="confirmButton">
      <b-button variant="outline-success" size="lg" :disabled="!answer" @click="confirmAnswer(answer)">Confirm Answer</b-button>
    </div>

    <div>
      <h5 class="successMessage" v-show="successMessageVisibility">{{successMessage}}</h5>
      <h5 class="failureMessage" v-show="failureMessageVisibility">{{failureMessage}}</h5>
      <h5 v-show="messageVisibility">{{message}}</h5>
    </div>

    <div>
      <h5 v-show="correctAnswerVisibility">{{correctAnswer}}</h5>
    </div>

  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "SoloGame",
  data() {
    return {
      askedQuestion: '',
      answer1: '',
      answer2: '',
      answer3: '',
      answer4: '',
      answer: '',
      quizName: localStorage.getItem('quizName'),
      quiz: '',
      questionNumber: '',
      questions: '',
      counter: '',
      correctAnswerCounter: '',
      successMessage: '',
      successMessageVisibility: false,
      failureMessage: '',
      failureMessageVisibility: false,
      message: '',
      messageVisibility: false,
      correctAnswer: '',
      correctAnswerVisibility: false
    }
  },
  methods: {
    getQuiz(quizName) {
      axios.get("http://localhost:8081/quiz/" + quizName, null)
        .then(response => {
          console.log(response.data)
          this.questionNumber = response.data.nbQuestion
          this.quiz = response.data
        })
        .catch(error => {
          console.log(error)
        })
    },
    getQuestions(quizName) {
      axios.get("http://localhost:8081/quiz/" + quizName + "/questions", null)
        .then(response => {
          console.log(response.data)
          this.questions = response.data
          this.startQuiz()
        })
        .catch(error => {
          console.log(error)
        })
    },
    startQuiz() {
      this.askedQuestion = this.questions[0].question
      this.answer1 = this.questions[0].possibleAnswers[0]
      this.answer2 = this.questions[0].possibleAnswers[1]
      this.answer3 = this.questions[0].possibleAnswers[2]
      this.answer4 = this.questions[0].possibleAnswers[3]
      this.counter = 0;
      this.correctAnswerCounter = 0
    },
    confirmAnswer(answer) {
      if (answer == this.questions[this.counter].answer) {
        this.answer = ''
        this.successMessageVisibility = true
        this.successMessage = "Correct answer!"
        this.correctAnswerCounter++
        setTimeout(() => this.successMessageVisibility = false + this.continueQuiz(), 2000)
      } else {
        this.answer = ''
        this.failureMessageVisibility = true
        this.correctAnswerVisibility = true
        this.failureMessage = "Wrong answer, better luck next time!"
        this.correctAnswer = "The correct answer was: " + this.questions[this.counter].answer
        setTimeout(() => { this.failureMessageVisibility = false, this.correctAnswerVisibility = false, this.continueQuiz() }, 2000)
      }
    },
    continueQuiz() {
      if (this.counter == (this.questionNumber - 1)) {
        let score = (this.correctAnswerCounter / this.questionNumber).toFixed(2)
        this.messageVisibility = true
        this.message = "The quiz is over. Your score was: " + score * 100 + "%"
      } else {
        this.counter++
        this.populateQuestionAndAnswers(this.counter)
      }
    },
    populateQuestionAndAnswers(questionNumber) {
      this.askedQuestion = this.questions[questionNumber].question
      this.answer1 = this.questions[questionNumber].possibleAnswers[0]
      this.answer2 = this.questions[questionNumber].possibleAnswers[1]
      this.answer3 = this.questions[questionNumber].possibleAnswers[2]
      this.answer4 = this.questions[questionNumber].possibleAnswers[3]
    }
  },
  created() {
    this.getQuiz(localStorage.getItem('quizName'))
    this.getQuestions(localStorage.getItem('quizName'))
  }
}
</script>

<style scoped>

.question {
  padding-top: 50px;
  padding-bottom: 20px;
}

.buttons {
  padding-bottom: 10px;
}

.confirmButton {
  padding-top: 40px;
  padding-bottom: 40px;
}

.successMessage {
  color: green;
}

.failureMessage {
  color: darkred;
}

</style>
