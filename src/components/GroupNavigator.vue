<template>
  <div class="Group-Navigator">
    <div class="head">
      <div class="group_name_wrapper">
        <span class="navigator_group_name">{{ group.groupName }}</span>
        <i class="fa fa-check-circle" aria-hidden="true" @click="clickGroup" v-if="!isClickedGroup"></i>
        <i class="fa fa-check-circle gray" aria-hidden="true" @click="clickGroup" v-else></i>
      </div>
      <div class="right_empty"></div>
    </div>
    <div class="contest_wrapper" v-if="!isClickedGroup" >
      <div v-for="item in contestInfos" :key="item.contestName">
        <div class="contest_name_wrapper">
          <div class="contest_name_center">
            <span class="contest_name">{{ item.contestName }}</span>
            <i class="fa fa-check-circle" aria-hidden="true" @click="clickContest(item.contestId)" v-if="!isClickedContest(item.contestId)"></i>
            <i class="fa fa-check-circle gray" aria-hidden="true" @click="clickContest(item.contestId)" v-else></i>
            <br/>
          </div>
        </div>
        <div class="contest_btn_wrapper" v-if="!isClickedContest(item.contestId)">
          <button class="hoverable_btn" v-for="problem in item.problems" :key="problem.name" @click="buttonClick(item.contestId, problem.problemId)">{{ problem.name }}</button>
        </div>
      </div>
    </div>

  </div>
</template>

<script>
/*****************************************************************
 ************************** import *******************************
 *****************************************************************/
// import util from '@/common/util.js'
export default {
  name: 'GroupNavigator',
  components: {},
  props: {
    group: Object
  },
  data () {
    return {
      contests: [],
      contestInfos: [],
      isClickedGroup: false,
      clickedContestList: []
    }
  },
  /*****************************************************************
   ********************** computed, watch ***********************
   *****************************************************************/
  computed: {},
  watch: {
  },
  /*****************************************************************
   ************************** Life-Cycle ***************************
   *****************************************************************/
  created () {
    let vm = this
    this.getContests().then(response => {
      this.contests = response.data
      for (let idx in this.contests) {
        let contest = vm.contests[idx]
        vm.getProblems(contest).then(response => {
          contest.problems = response.data
          vm.contestInfos.push(contest)
        })
      }
    })
  },
  mounted () {},
  updated () {},
  beforeDestroy () {},
  destroyed () {},
  /*****************************************************************
   ********************** User-Defined Methods *********************
   *****************************************************************/
  methods: {
    buttonClick (contestId, problemId) {
      this.$router.push('/contest/' + contestId + '/' + problemId)
    },
    getProblems (contest) {
      return new Promise((resolve, reject) => {
        let uri = 'api/contests/' + contest.contestId + '/problems'
        let headers = { authorization: localStorage.getItem('authorization').toString() }
        this.$axios.get(uri, {
          headers: headers
        }).then((response) => {
          resolve(response.data)
        }).catch((err) => {
          reject(err)
        })
      })
    },
    getContests () {
      return new Promise((resolve, reject) => {
        let groupId = this.group.groupId
        let uri = 'api/groups/' + groupId + '/contests'
        let headers = { authorization: localStorage.getItem('authorization').toString() }
        this.$axios.get(uri, {
          headers: headers
        }).then(response => {
          resolve(response.data)
        }).catch(err => {
          reject(err)
        })
      })
    },
    clickGroup () {
      this.isClickedGroup = !this.isClickedGroup
    },
    clickContest (contestId) {
      let list = this.clickedContestList.filter(id => id !== contestId)
      if (list.length === this.clickedContestList.length) {
        this.clickedContestList.push(contestId)
      } else {
        this.clickedContestList = list
      }
    },
    isClickedContest (contestId) {
      return this.clickedContestList.includes(contestId)
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
button.hoverable_btn {
  color: black;
  background-color: white;
  height: 2em;
  width: 5em;
  font-size: 1em;
  border: 2px solid black;
  border-radius: 10px;
  margin: 3px;
}
button.hoverable_btn:hover {
  background-color:rgb(111, 113, 116);
  color: white;
}
span.navigator_group_name {
  font-weight: 700;
  font-size: 2em;
  text-decoration: underline;
}
.head{
  display: flex;
  flex-direction: row;
}
.group_name_wrapper{
  width: 90%;
  display: flex;
  flex-direction: row;
  justify-content: flex-end;
}
.right_empty{
  width: 10%;
}
span.contest_name{
  font-weight: 600;
  color: dimgray;
  font-size: large;

}
.contest_name_wrapper{
  margin-top: 1em;
  display: flex;
  flex-direction: row;
  justify-content: center;
}
.contest_name_center{
  padding: 0.1em;
  display: flex;
  flex-direction: row;
  justify-content: center;
}
.gray {
  color: dimgray;
}

</style>
