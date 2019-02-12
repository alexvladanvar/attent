<template>
  <header>
    <nav>
      <ul>
        <router-link class="item" :to="{ name: 'home' }">Home</router-link>
        <router-link class="item" :to="{ name: 'signup' }">Sign up</router-link>
      </ul>
      <ul>
        <router-link class="item" :to="{ name: 'about' }">About</router-link>
      </ul>
    </nav>
  </header>
</template>

<script>
export default {};
</script>

<style scoped>
header {
}

nav {
  display: flex;
  background: rgb(253, 251, 251);
  background: #2196f3;
  justify-content: space-between;
  height: auto;
}

ul {
  display: flex;
  height: auto;
}

.item {
  background: #2196f3;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 10px;
  text-decoration: none;
  list-style-type: none;
  height: 20px;
  text-decoration: none;
  color: white;
}

.router-link-exact-active {
  border-bottom: 3px solid #ffc107;
}
</style>
