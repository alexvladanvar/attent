<template>
  <div>
    <h1>Generate QR-code for your group</h1>
    <div class="qr-wrapper">
      <div class="form-container">
        <h1>Group Data</h1>
        <form>
          <v-text-field
            v-model="lesson"
            :error-messages="emailErrors"
            label="Lesson"
            required
            @input="$v.email.$touch()"
            @blur="$v.email.$touch()"
          ></v-text-field>
          <v-text-field
            v-model="group"
            :error-messages="passwordErrors"
            label="Group"
            required
            @input="$v.password.$touch()"
            @blur="$v.password.$touch()"
          ></v-text-field>
          <v-text-field
            v-model="time"
            label="Time"
            required
            @input="$v.password.$touch()"
            @blur="$v.password.$touch()"
          ></v-text-field>
          <v-btn :loading="loading" @click="submit" color="light-blue" dark
            >Generate</v-btn
          >
        </form>
      </div>
      <qr-code
        v-if="qr"
        class="qr"
        :text="qr"
        size="400"
        color="#f1c40f"
        bg-color="#3498db"
        error-level="L"
      ></qr-code>
      <div v-else class="placeholder"></div>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'
export default {
  data() {
    return {
      lesson: null,
      group: null,
      time: null
    }
  },
  computed: {
    ...mapState(['qr']),
    text() {
      return this.lesson + this.group + this.time
    }
  },
  methods: {
    submit() {
      this.$store.dispatch('generateQR', this.text)
    }
  }
}
</script>

<style>
.qr-wrapper {
  border: 1px solid rgb(184, 184, 184);
  width: 80%;
  height: 500px;
  margin: 10px auto;
  padding: 10px;
  display: flex;
  justify-content: space-evenly;
  align-items: center;
}

h1 {
  text-align: center;
}
.form-container {
  width: 300px;
}

.placeholder {
  width: 400px;
  height: 400px;
  border: 1px solid rgb(184, 184, 184);
}
</style>
