import Vue from 'vue'
import Vuex from 'vuex'
import router from './router'

Vue.use(Vuex)

import {
  signUp,
  logIn,
  getUser,
  getUserData,
  logout,
  generateQR,
  getAttendanceData
} from './services/UserServices'

export default new Vuex.Store({
  state: {
    loading: false,
    user: false,
    userData: null,
    qr: null,
    attendances: null
  },
  mutations: {
    SET_LOADING(store, loading) {
      store.loading = loading
    },
    LOG_IN(store) {
      store.user = true
    },
    LOG_OUT(store) {
      store.user = null
      store.userData = null
      router.push('/')
    },
    GET_USER_DATA(store, data) {
      store.userData = data
      console.log('qweqwe', data)
    },
    GENERATE_QR(store, text) {
      store.qr = text
    },
    SET_ATTENDANCES(store, attendances) {
      store.attendances = attendances
    }
  },
  actions: {
    setLoading({ commit }, loading) {
      commit('SET_LOADING', loading)
    },
    submitSignup({ commit }, data) {
      commit('SET_LOADING', true)

      return signUp(data).then(data => {
        commit('SET_LOADING', false)
        return data
      })
    },
    submitLogin({ commit }, data) {
      commit('SET_LOADING', true)

      return logIn(data).then(resData => {
        if (resData.data.success) commit('LOG_IN')
        commit('SET_LOADING', false)
        console.log(resData)
        return resData.data.success
      })
    },
    async logout({ commit }) {
      await logout()
      commit('LOG_OUT')
      return true
    },
    getUser({ commit }) {
      return getUser().then(data => {
        console.log(data)

        if (data.data.success) {
          commit('LOG_IN')
          router.push('/profile')
        }
      })
    },
    getUserData({ commit }) {
      getUserData().then(data => {
        console.log(data.data)
        commit('GET_USER_DATA', data.data)
      })
    },
    async getAttendances({ commit }) {
      const res = await getAttendanceData()

      commit('SET_ATTENDANCES', res.data)

      console.log(res.data)
    },
    async generateQR({ commit }) {
      const res = await generateQR({ lesson: 'qwe', date: 'qwe' })

      console.log(res)
      commit('GENERATE_QR', res.data.hash)
    }
  }
})
