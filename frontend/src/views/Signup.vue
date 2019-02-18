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
      <v-text-field
        v-if="select === 'Student'"
        v-model="group"
        :error-messages="groupErrors"
        label="Group"
        required
        @input="$v.group.$touch()"
        @blur="$v.group.$touch()"
      ></v-text-field>

      <v-btn @click="submit">Sign Up</v-btn>
    </form>
  </div>
</template>

<script>
import { validationMixin } from 'vuelidate'
import { required, email, minLength } from 'vuelidate/lib/validators'
export default {
  mixins: [validationMixin],

  validations: {
    name: { required, minLength: minLength(4) },
    email: { required, email },
    select: { required },
    group: { required }
  },

  data: () => ({
    group: '',
    name: '',
    email: '',
    select: null,
    items: ['Student', 'Teacher'],
    checkbox: false
  }),

  computed: {
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
      return errors
    },
    emailErrors() {
      const errors = []
      if (!this.$v.email.$dirty) return errors
      !this.$v.email.email && errors.push('Must be valid e-mail')
      !this.$v.email.required && errors.push('E-mail is required')
      return errors
    },
    groupErrors() {
      const errors = []
      if (!this.$v.group.$dirty) return errors
      !this.$v.group.required && errors.push('Group is required')
      return errors
    }
  },

  methods: {
    submit() {
      this.$v.$touch()
      const { nameErrors, emailErrors, groupErrors } = this
      const errors = [...nameErrors, ...emailErrors, ...groupErrors]

      if (!errors.length) {
        console.log('submitting')
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
