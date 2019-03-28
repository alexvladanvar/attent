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
        >Log In</v-btn
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
    email: '',
    password: ''
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
    ...mapActions(['setLoading', 'submitSignup', 'submitLogin']),
    async submit() {
      this.$v.$touch()
      const { emailErrors, passwordErrors } = this
      const errors = [...emailErrors, ...passwordErrors]

      if (!errors.length) {
        // call submit action
        const { email, password } = this
        const res = await this.submitLogin({ login: email, password })
        console.log(res)
        if (res) {
          this.$router.push('/')
        }
      }
    },
    clear() {
      this.$v.$reset()
      this.name = ''
      this.password = ''
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
