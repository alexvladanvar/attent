<template>
  <div class="profile">
    <h1>Your attendance</h1>
    <div v-if="userData != null">
      <h2>{{ userData.login }}'s account</h2>
      <h3>Role: {{ userData.role === 1 ? 'Teacher' : 'Student' }}</h3>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'
export default {
  computed: {
    ...mapState(['user', 'userData'])
  },
  created() {
    this.$store.dispatch('getUserData')
  }
}
</script>

<style scoped>
.profile {
  margin: 10px;
}
</style>
