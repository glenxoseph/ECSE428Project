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
      <router-link to="/bannedAccounts"><b-button class="p3" type="submit" size="lg" variant="dark">Ban/Unban Accounts</b-button></router-link>
    </div>

  </div>

</template>


