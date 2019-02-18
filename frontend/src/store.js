import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

import { signUp } from './services/UserServices'

export default new Vuex.Store({
  state: {
    loading: false,
    user: null
  },
  mutations: {
    SET_LOADING(store, loading) {
      store.loading = loading
    },
    SIGN_UP(store, data) {
      store.user = data
    }
  },
  actions: {
    setLoading({ commit }, loading) {
      commit('SET_LOADING', loading)
    },
    async submitSignup({ commit }, data) {
      commit('SET_LOADING', true)

      try {
        await signUp(data)
        commit('SIGN_UP', data)

        commit('SET_LOADING', false)
      } catch (error) {
        console.log(error)
      }
    }
  }
})
