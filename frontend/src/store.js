import Vue from 'vue'
import Vuex from 'vuex'
import router from './router'

Vue.use(Vuex)

import { signUp, logIn, getUser, getUserData } from './services/UserServices'

export default new Vuex.Store({
  state: {
    loading: false,
    user: false,
    userData: null,
    qr: null
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
    },
    GET_USER_DATA(store, data) {
      store.userData = data
    },
    GENERATE_QR(store, text) {
      store.qr = text
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
    logout({ commit }) {
      commit('LOG_OUT')
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
    generateQR({ commit }, text) {
      commit('GENERATE_QR', text)
    }
  }
})
