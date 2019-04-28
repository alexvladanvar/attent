<template>
  <div v-if="attendances">
    <table class="table">
      <thead>
        <th>Lesson</th>
        <th>Teacher</th>
        <th>Attended</th>
      </thead>
      <tr
        v-for="(item, index) in attendances"
        :key="index"
        :class="item.attended ? 'attended' : 'absent'"
      >
        <td>{{ item.lesson.name }}</td>
        <td>
          {{
            item.lesson.teacher.lastName + ' ' + item.lesson.teacher.firstName
          }}
        </td>
        <td v-if="item.attended">
          {{ item.lesson.attended }}
          <v-btn flat icon color="green darken-2">
            <v-icon>check</v-icon>
          </v-btn>
        </td>
        <td v-else>
          <v-btn flat icon color="red darken-2">
            <v-icon>highlight_off</v-icon>
          </v-btn>
        </td>
      </tr>
    </table>
  </div>
</template>

<script>
import { mapState } from 'vuex'
export default {
  computed: {
    ...mapState(['attendances'])
  },
  mounted() {
    this.$store.dispatch('getAttendances')
  }
}
</script>

<style>
table {
  width: 70%;
  border: 1px solid rgb(219, 219, 219);
  text-align: center;
  margin: 30px auto;
  font-size: 20px;
}

td {
  border: 1px solid rgb(219, 219, 219);
}
tr {
  cursor: pointer;
}
tr.attended {
  background: rgb(163, 255, 163);
}

tr.attended:hover {
  background: rgb(129, 252, 129);
}
tr.absent {
  background: rgb(255, 188, 188);
}
tr.absent:hover {
  background: rgb(247, 146, 146);
}
</style>
