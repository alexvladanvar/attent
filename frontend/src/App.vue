<template>
  <v-app>
    <app-header></app-header>
    <v-content>
      <router-view />
    </v-content>
  </v-app>
</template>

<script>
import AppHeader from '@/components/AppHeader'
export default {
  components: {
    AppHeader
  },
  created() {
    this.$store.dispatch('getUser')
  }
}
</script>

<style></style>
