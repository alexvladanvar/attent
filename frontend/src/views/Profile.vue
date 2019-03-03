<template>
  <div class="profile">
    <h1>Profile Page</h1>
    <p>
      username:
      <b>{{ user.login }}</b>
    </p>
    <p>
      role:
      <b>{{ user.role }}</b>
    </p>
    <p>
      group:
      <b>{{ user.group }}</b>
    </p>
  </div>
</template>

<script>
import { mapState } from 'vuex'
export default {
  computed: {
    ...mapState(['user'])
  }
}
</script>

<style scoped>
.profile {
  margin: 10px;
}
</style>
