<template>
  <div>logging out..</div>
</template>

<script>
export default {
  created: function() {
    this.$store.dispatch('logout')
    this.$router.push('/')
  }
}
</script>

<style scoped></style>
