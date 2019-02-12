<template>
  <div id="app">
    <app-header></app-header>
    <div class="container">
      <router-view />
    </div>
  </div>
</template>

<script>
import AppHeader from '@/components/AppHeader'
export default {
  components: {
    AppHeader
  }
}
</script>

<style>
* {
  margin: 0;
  padding: 0;
  font-family: 'Lato';
  color: #212121;
}

.container {
  margin: 15px;
}

#app {
}
</style>
