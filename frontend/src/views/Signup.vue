<template>
  <div class="form-container">
    <h1>Sign Up</h1>
    <form>
      <v-text-field
        v-model="name"
        :error-messages="nameErrors"
        :counter="30"
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
      <v-text-field
        v-model="password2"
        :error-messages="password2Errors"
        label="Confirm Password"
        required
        type="password"
        @input="$v.password2.$touch()"
        @blur="$v.password2.$touch()"
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

      <v-text-field
        v-if="select === 'Student'"
        v-model="group"
        :error-messages="groupErrors"
        label="Group"
        required
        @input="$v.group.$touch()"
        @blur="$v.group.$touch()"
      ></v-text-field>

      <v-btn :loading="loading" @click="submit" color="light-blue" dark
        >Sign Up</v-btn
      >
    </form>
  </div>
</template>

<script>
import { validationMixin } from 'vuelidate'
import {
  required,
  email,
  minLength,
  maxLength,
  sameAs
} from 'vuelidate/lib/validators'
import { mapState, mapActions } from 'vuex'
export default {
  mixins: [validationMixin],

  validations: {
    name: { required, minLength: minLength(4), maxLength: maxLength(30) },
    email: { required, email },
    password: { required, minLength: minLength(5), maxLength: maxLength(100) },
    select: { required },
    group: { required },
    password2: { required, sameAs: sameAs('password') }
  },

  data: () => ({
    group: '',
    name: '',
    email: '',
    password: '',
    password2: '',
    select: null,
    items: ['Student', 'Teacher']
  }),

  computed: {
    ...mapState(['loading']),
    selectErrors() {
      const errors = []
      if (!this.$v.select.$dirty) return errors
      !this.$v.select.required && errors.push('Item is required')
      return errors
    },
    nameErrors() {
      const errors = []
      if (!this.$v.name.$dirty) return errors
      !this.$v.name.minLength &&
        errors.push('Name must be at least 4 characters long')
      !this.$v.name.required && errors.push('Name is required.')
      !this.$v.name.maxLength &&
        errors.push('Name must be at most 30 character long')
      return errors
    },
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
    },
    groupErrors() {
      const errors = []
      console.log(this.select)
      if (!this.$v.group.$dirty || this.select === 'Teacher') return errors
      !this.$v.group.required && errors.push('Group is required')
      return errors
    },
    password2Errors() {
      const errors = []
      if (!this.$v.password2.$dirty) return errors
      !this.$v.password2.required && errors.push('You must confirm password')
      !this.$v.password2.sameAs && errors.push('Passwords must match')
      return errors
    }
  },

  methods: {
    ...mapActions(['setLoading', 'submitSignup']),
    async submit() {
      this.$v.$touch()
      const {
        nameErrors,
        emailErrors,
        groupErrors,
        selectErrors,
        password2Errors,
        passwordErrors
      } = this
      const errors = [
        ...nameErrors,
        ...emailErrors,
        ...groupErrors,
        ...passwordErrors,
        ...selectErrors,
        ...password2Errors
      ]

      console.log(errors)
      if (!errors.length) {
        const { email, password, group, select } = this
        await this.submitSignup({
          login: email,
          password,
          group,
          role: select === 'Teacher' ? 1 : 2
        })
        this.$router.push('/login')
      }
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
