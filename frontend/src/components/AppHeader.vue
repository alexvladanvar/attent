<template>
  <v-toolbar dark flat dense color="teal lighten-1">
    <v-toolbar-items>
      <v-btn :to="`/${user ? 'profile' : ''}`" flat>
        <v-icon>home</v-icon>
      </v-btn>
      <v-btn v-if="!user" flat to="/signup">Sign Up</v-btn>
      <v-btn v-if="!user" flat to="/login">Log In</v-btn>
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
    ...mapState(['user'])
  }
}
</script>

<style></style>
