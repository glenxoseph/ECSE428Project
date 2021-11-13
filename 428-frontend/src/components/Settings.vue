<template>

  <div>
    <router-link to="/home"><b-button class="p3" type="submit" size="sm" variant="dark">Back to Home</b-button></router-link>

    <hr style="width: 50%">

    <h1> Settings </h1>

    <div class="m-5">
      <router-link to="/changePassword"><b-button class="p3" type="submit" size="lg" variant="dark">Change Password</b-button></router-link>
    </div>

    <div class="m-5">
      <router-link to="/changeEmail"><b-button class="p3" type="submit" size="lg" variant="dark">Change Email</b-button></router-link>
    </div>
    <div class="m-5">
      <router-link to="/deleteAccount"><b-button class="p3" type="submit" size="lg" variant="dark">Delete Account</b-button></router-link>
    </div>

    <div class="m-5">
      <b-button v-b-modal.modal-prevent-closing class="p3" type="submit" size="lg" variant="dark">Ban/Unban Accounts</b-button>
    </div>

    <b-modal id="modal-prevent-closing" ref="modal" title="Administrator password" @show="resetModal" @hidden="resetModal">
      <form ref="form">
        <b-form-group label="Password" label-for="password-input" :state="passwordState" placeholder="Please enter the administrator password">
          <b-form-input id="password-input" type="password" v-model="password" :state="passwordState"></b-form-input>
        </b-form-group>
      </form>

      <template #modal-footer="{ cancel, confirm }">
        <b-button size="md" @click="resetModal">
          Cancel
        </b-button>
        <b-button size="md" variant="success" @click="handleConfirm(password)">
          Confirm
        </b-button>
      </template>

    </b-modal>

    <p class="errorMessage" v-show="errorMessageVisibility">{{errorMessage}}</p>

  </div>

</template>

<script>
export default {
  data() {
    return {
      password: '',
      passwordState: null,
      errorMessage: '',
      errorMessageVisibility: false
    }
  },
  methods: {
    resetModal() {
      this.password = ''
      this.passwordState = null
      this.$bvModal.hide('modal-prevent-closing')
    },
    handleConfirm(password) {
      if (!(password == "password")) {
        console.log(password)
        this.$bvModal.hide('modal-prevent-closing')
        this.errorMessageVisibility = true
        this.errorMessage = "Incorrect password"
        setTimeout(() => this.errorMessageVisibility = false, 4000)
      } else {
        this.$router.push('/bannedAccounts')
      }
    }
  }
}
</script>
