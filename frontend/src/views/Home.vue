<template>
  <div class="home">
    <div class="main">
      <h1>Welcome to Attent</h1>
      <div class="buttons">
        <button class="btn">Sign Up</button>
        <button class="btn">Log In</button>
      </div>
    </div>
  </div>
</template>

<script>
// @ is an alias to /src
import HelloWorld from "@/components/HelloWorld.vue";

export default {
  name: "home",
  components: {
    HelloWorld
  }
};
</script>

<style>
.home {
  display: flex;
  justify-content: center;
  height: 100%;
}

.buttons {
  display: flex;
  align-items: center;
  justify-content: space-evenly;
  margin-top: 20px;
}

.btn {
  cursor: pointer;
  border: 0;
  padding: 15px 15px 15px 15px;
  color: white;
  background: #1ee484;
  border-radius: 10px;
  font-size: 18px;
}
</style>
