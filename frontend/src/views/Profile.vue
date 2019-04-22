<template>
  <div class="profile">
    <h1>Attent</h1>
    <div v-if="userData != null && userData.role === 1">
      <h2>{{ userData.login }}'s account</h2>
      <h3>Role: {{ userData.role === 1 ? 'Teacher' : 'Student' }}</h3>
    </div>
    <attendances v-if="userData && userData.role === 2"></attendances>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import Attendances from '@/components/Attendances'

export default {
  components: {
    Attendances
  },
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
