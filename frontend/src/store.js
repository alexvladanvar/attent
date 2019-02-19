import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

import { signUp, logIn } from './services/UserServices'

export default new Vuex.Store({
  state: {
    loading: false,
    user: null
  },
  mutations: {
    SET_LOADING(store, loading) {
      store.loading = loading
    },
    LOG_IN(store, data) {
      store.user = data
    }
  },
  actions: {
    setLoading({ commit }, loading) {
      commit('SET_LOADING', loading)
    },
    async submitSignup({ commit }, data) {
      commit('SET_LOADING', true)

      return signUp(data).then(data => {
        commit('SET_LOADING', false)
        return data
      })
    }
  }
})
