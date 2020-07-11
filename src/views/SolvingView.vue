<template>
  <div class="SolvingView">
    <div class="left-view">
      <div class="group-navigator-wrapper" v-for="group in groups" :key="group.groupId">
        <GroupNavigator :group="group"></GroupNavigator>
      </div>
    </div>
    <div class="center-view">
      <Problem></Problem>
    </div>
    <div class="right-view">
      <div v-for="group in groupsInContest" :key="group.groupId">
        <Standings :group="group"></Standings>
      </div>
    </div>
  </div>
</template>

<script>
/*****************************************************************
 ************************** import *******************************
 *****************************************************************/
import Problem from '@/components/Problem'
import GroupNavigator from '@/components/GroupNavigator'
import Standings from '@/components/Standings'
export default {
  name: 'SolvingView',
  components: {
    Problem: Problem,
    GroupNavigator: GroupNavigator,
    Standings: Standings
  },
  props: {
  },
  data () {
    return {
      groupsInContest: []
    }
  },
  /*****************************************************************
  ********************** computed, watch ***********************
  *****************************************************************/
  computed: {
    groups () {
      return this.$store.state.groups
    }
  },
  watch: {
    groups (changed) {
      this.groupsInContest = []
      for (let idx in changed) {
        let group = changed[idx]
        this.isGroupInContest(group).then(res => {
          if (res === true) {
            this.groupsInContest.push(group)
          }
        })
      }
    },
    $route (to, from) {
      if (to.params.contestId === from.params.contestId) {
      } else {
        this.groupsInContest = []
        for (let idx in this.groups) {
          let group = this.groups[idx]
          this.isGroupInContest(group).then(res => {
            if (res === true) {
              this.groupsInContest.push(group)
            }
          })
        }
      }
    }
  },
  /*****************************************************************
  ************************** Life-Cycle ***************************
  *****************************************************************/
  created () {
    this.$store.dispatch('GET_GROUPS')
  },
  mounted () {
  },
  beforeDestroy () {
  },
  destroyed () {
  },
  /*****************************************************************
   ********************** User-Defined Methods *********************
   *****************************************************************/
  methods: {
    isGroupInContest (group) {
      return new Promise((resolve, reject) => {
        this.$axios.get(`api/groups/${group.groupId}/contests`, {
          headers: {
            authorization: localStorage.getItem('authorization').toString()
          }
        }).then(res => {
          let contests = res.data.data
          for (let idx in contests) {
            let contest = contests[idx]
            if (contest.contestId.toString() === this.$route.params.contestId) {
              resolve(true)
            }
          }
          resolve(false)
        }).catch(err => {
          console.error(err)
          reject(err)
        })
      })
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.SolvingView{
  display: flex;
  flex-direction: row;
}
.left-view{
  width: 15%;
  margin-left: 3%;
  margin-top: 10em;
}
.center-view{
  margin-left: 5%;
  margin-right: 5%;
  width: 50%;
  margin-top: 3em;
}
.right-view{
  width: 23%;
  margin-right: 3%;
  margin-top: 10em;
}
div .group-navigator-wrapper{
  margin-bottom: 2em;
  border: dotted 2px black;
}
</style>
