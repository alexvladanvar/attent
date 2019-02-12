<template>
  <div class="form-container">
    <h1>Sign Up</h1>
    <form>
      <v-text-field
        v-model="name"
        :error-messages="nameErrors"
        :counter="10"
        label="Name"
        required
        @input="$v.name.$touch()"
        @blur="$v.name.$touch()"
      ></v-text-field>
      <v-text-field
        v-model="email"
        :error-messages="emailErrors"
        label="E-mail"
        required
        @input="$v.email.$touch()"
        @blur="$v.email.$touch()"
      ></v-text-field>
      <v-select
        v-model="select"
        :items="items"
        :error-messages="selectErrors"
        label="Role"
        required
        @change="$v.select.$touch()"
        @blur="$v.select.$touch()"
      ></v-select>

      <v-btn @click="submit">Sign Up</v-btn>
    </form>
  </div>
</template>

<script>
export default {}
</script>

<style scoped>
.form-container {
  max-width: 400px;
  margin: 0 auto;
}
</style>
