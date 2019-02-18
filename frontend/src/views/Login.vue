<template>
  <div class="form-container">
    <h1>Log In</h1>
    <form>
      <v-text-field
        v-model="email"
        :error-messages="emailErrors"
        label="E-mail"
        required
        @input="$v.email.$touch()"
        @blur="$v.email.$touch()"
      ></v-text-field>
      <v-text-field
        v-model="password"
        :error-messages="passwordErrors"
        :counter="100"
        label="Password"
        type="password"
        required
        @input="$v.password.$touch()"
        @blur="$v.password.$touch()"
      ></v-text-field>

      <v-btn :loading="loading" @click="submit" color="light-blue" dark
        >Sign Up</v-btn
      >
    </form>
  </div>
</template>

<script>
import { validationMixin } from 'vuelidate'
import { required, email, minLength, maxLength } from 'vuelidate/lib/validators'
import { mapState, mapActions } from 'vuex'
export default {
  mixins: [validationMixin],

  validations: {
    email: { required, email },
    password: { required, minLength: minLength(5), maxLength: maxLength(100) }
  },

  data: () => ({
    email: 'qweqwe@qweqwe.qwe',
    password: 'qweqwe'
  }),

  computed: {
    ...mapState(['loading']),

    emailErrors() {
      const errors = []
      if (!this.$v.email.$dirty) return errors
      !this.$v.email.email && errors.push('Must be valid e-mail')
      !this.$v.email.required && errors.push('E-mail is required')
      return errors
    },
    passwordErrors() {
      const errors = []
      if (!this.$v.password.$dirty) return errors
      !this.$v.password.required && errors.push('Password is required')
      !this.$v.password.minLength &&
        errors.push('Password must be at least 5 character long')
      !this.$v.password.maxLength &&
        errors.push('Password must be at most 100 character long')
      return errors
    }
  },

  methods: {
    ...mapActions(['setLoading', 'submitSignup']),
    submit() {
      this.$v.$touch()
      const { nameErrors, emailErrors, groupErrors } = this
      const errors = [...nameErrors, ...emailErrors, ...groupErrors]

      if (!errors.length) {
        // call submit action
        const { email, password, group, select } = this
        this.submitSignup({ email, password, group, role: select })
      }
    },
    clear() {
      this.$v.$reset()
      this.name = ''
      this.email = ''
      this.select = null
      this.checkbox = false
    }
  }
}
</script>

<style scoped>
.form-container {
  max-width: 400px;
  margin: 0 auto;
  padding: 15px;
}
</style>
