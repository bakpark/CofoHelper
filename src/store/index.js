import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    members: ['MochaLatte', 'bakpark', 'johny1', 'bonoky', 'sw1124.han', 'psykibaek'],
    contests: [1343],
    beforeContests: [1367, 1366, 1353, 1369],
    submits: {
      'MochaLatte': [],
      'bakpark': [],
      'johny1': [],
      'bonoky': [],
      'sw1124.han': [],
      'psykibaek': []
    },
    readyToShow: ''
  },
  mutations: {
    CHANGE_TABLE_DATA (state, [tableKey, data]) {
      state.submits[tableKey] = data
    },
    CHANGE_READY_STATUS (state, payload) {
      state.readyToShow = payload.status
      console.log('readyToShow:', state.readyToShow)
    }
  },
  actions: {},
  modules: {}
})
