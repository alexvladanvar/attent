<template>
  <v-jumbotron>
    <v-container fill-height>
      <v-layout align-center>
        <v-flex>
          <h3 class="display-3">Welcome to the Attent</h3>

          <span class="subheading"
            >Attent is aimed to make attendance checking more covenient</span
          >

          <v-divider class="my-3"></v-divider>

          <div class="title mb-3">Join now!</div>

          <v-btn class="mx-0" color="green accent-3" dark to="/signup"
            >Sign Up</v-btn
          >
        </v-flex>
      </v-layout>
    </v-container>
  </v-jumbotron>
</template>

<script>
// @ is an alias to /src

export default {
  name: 'home'
}
</script>

<style></style>
