import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    members: ['MochaLatte', 'bakpark', 'johny1', 'bonoky', 'sw1124.han', 'psykibaek'],
    contests: [1353, 1369],
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
