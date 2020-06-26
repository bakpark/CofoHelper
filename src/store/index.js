import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    'resultTable': [],
    'wResultTable': []
  },
  mutations: {
    CHANGE_TABLE_DATA (state, [tableKey, data]) {
      state[tableKey] = data
      console.log(state)
      console.log('CHANGE_TABLE_DATA of key:', tableKey)
    }
  },
  actions: {},
  modules: {}
})
