<template>
  <v-toolbar dark flat dense color="blue lighten-1">
    <v-toolbar-items>
      <v-btn :to="`/${user ? 'profile' : ''}`" flat>
        <v-icon>home</v-icon>
      </v-btn>
      <v-btn v-if="!user" flat to="/signup">Sign Up</v-btn>
      <v-btn v-if="user" flat>{{ userData.login }}</v-btn>

      <v-btn v-if="!user" flat to="/login">Log In</v-btn>
      <v-btn v-if="user && userData && userData.role === 1" flat to="/generate"
        >Genarate</v-btn
      >
    </v-toolbar-items>
    <v-spacer class="spacer"></v-spacer>
    <v-toolbar-items>
      <v-btn :to="`/${user ? 'logout' : 'about'}`" flat>
        <span>{{ user ? 'Logout' : 'About' }}</span>
      </v-btn>
    </v-toolbar-items>
  </v-toolbar>
</template>

<script>
import { mapState } from 'vuex'
export default {
  computed: {
    ...mapState(['user', 'userData'])
  }
}
</script>

<style></style>
