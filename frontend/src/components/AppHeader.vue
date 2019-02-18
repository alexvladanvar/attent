<template>
  <v-toolbar dark flat dense color="teal lighten-1">
    <v-toolbar-items>
      <v-btn to="/" flat>
        <v-icon>home</v-icon>
      </v-btn>
      <v-btn flat to="/signup">Sign Up</v-btn>
      <v-btn flat to="/login">Log In</v-btn>
    </v-toolbar-items>
    <v-spacer class="spacer"></v-spacer>
    <v-toolbar-items>
      <v-btn to="/about" flat>
        <span>About</span>
      </v-btn>
    </v-toolbar-items>
  </v-toolbar>
</template>

<script>
export default {}
</script>

<style></style>
