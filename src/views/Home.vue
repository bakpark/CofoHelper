<style scoped>
.total {
  display: flex;
}
.main_view {
  display: flex;
  justify-content: flex-start;
}
.main_view .elements_container {
  max-width: 30vw;
  min-width: 20vw;
  margin: 20px;
  height: 40vh;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: flex-start;
  border: 1px solid black;
}
.main_view .table_container {
}

</style>
<template>
  <div class="total">
    <div class="main_view">
      <div class="elements_container gropus">
        <strong class="groups_header" style="font-size: 20px;">그룹이름</strong>
        <div
          v-for="(group) in $store.state.groups"
          :key="'group' + group.groupId"
          v-on:click="clickGroup(group.groupId)"
          style="cursor: pointer;"
        >
          <div v-if="group.groupId == curGroupId" style="background-color: red;">{{ group.groupName }}</div>
          <div v-else>{{ group.groupName }}</div>
        </div>
        <strong style="font-size: 20px;">현재 그룹 멤버</strong>
        <div v-for="(member, index) in members" :key="index">{{member.handle}}</div>
      </div>
      <div class="elements_container contests">
        <div class="groups_header">Contest이름</div>
        <div v-for="(contest) in contests" :key="'contest'+contest.contestId" >
          <span v-on:click="clickContest(contest.contestId)" style="cursor: pointer;">
            <span v-if="contest.contestId == curContestId" style="background-color:red;">{{ contest.contestName }}</span>
            <span v-else>{{ contest.contestName }}</span>
          </span>
          <button v-on:click="deleteContest(contest.contestId)">X</button>
        </div>
        <div v-if="$store.state.groups.length > 0">
          <span>콘테스트 추가</span>
          <input type="text" v-model="newContestName">
          <button v-on:click="addContest">추가하기</button>
        </div>
      </div>
      <div class="table_container contest_info">
        <Table
            v-for="(contestInfo, index) in contestInfos"
            :key="index"
            :columns="contestInfo.columns"
            :rows="contestInfo.rows"
            :contestId="Number(contestInfo.contestId)"
          ></Table>
          <div v-if="$store.state.groups.length > 0">
          <span>문제 추가</span>
          <input type="text" v-model="newProblemName">
          <button v-on:click="addContest">추가하기</button>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
/* eslint-disable */
import Table from "@/components/Table.vue";
import lodash from "lodash";
import util from "@/common/util.js";
import constants from "@/common/constants.js";

export default {
  data() {
    return {
      // group state
      curGroupId: 0,
      members: [],

      // contest state
      curContestId: 0,
      contests: [],
      newContestName: '',

      // problem state
      newProblemName: '',
      contestInfos: [],
    };
  },
  watch: {
  },
  methods: {
    clickGroup(groupId) {
      this.curGroupId = groupId;
      // contests
      this.$axios
        .get(`api/groups/${groupId}/contests`, {
          headers: {
            authorization: localStorage.getItem("authorization").toString()
          }
        })
        .then(res => {
          this.contests = res.data.data;
        });

      // members
      this.$axios
        .get(`api/groups/${groupId}/users`, {
          headers: {
            authorization: localStorage.getItem("authorization").toString()
          }
        })
        .then(res => {
          this.members = res.data.data;
        });
    
    },
    async clickContest (contestId){
      this.curContestId = contestId;
      let res = await this.$axios.get(
        `api/contests/${this.curContestId}/problems`,
        {
          headers: {
            authorization: localStorage.getItem("authorization").toString()
          }
        }
      );
      const problems = res.data.data.map(data => data.name);

      let res2 = await this.$axios.get(`api/groups/${this.curGroupId}/users`, {
        headers: {
          authorization: localStorage.getItem("authorization").toString()
        }
      });
      const members = res2.data.data.map(data => data.handle);

      const contestsObj = {};
      problems.forEach((name) => {
        let strs = name.split('-')
        const contestId = strs[0]
        const problemindex = strs[1]
        if(contestsObj[contestId] == undefined)
          contestsObj[contestId] = []
        contestsObj[contestId].push(strs[1])
      })

      
      let contestInfos = [];
      for(const contestId of Object.keys(contestsObj)){
        const problemIndexes = contestsObj[contestId]

        let submissions = await util.getSubmissions(members)
        submissions = util.filterSubmissions(submissions, contestId, problemIndexes)
        const contestInfo = util.getContestTableInfo(submissions, members, contestId, problemIndexes)
        contestInfos.push(contestInfo)
      }
      this.contestInfos = contestInfos
    
    },
    addContest() {
      this.$axios.post(`api/groups/${this.curGroupId}/contests`,
      { contestName: this.newContestName },
      {
        headers:{
          authorization: localStorage.getItem('authorization').toString()
        }
      })
        .then(res => {
          this.clickGroup(this.curGroupId)
        })
    },
    deleteContest(contestId) {
      this.$axios.delete(`api/contests/${contestId}`,
      {
        headers:{
          authorization: localStorage.getItem('authorization').toString()
        }
      })
        .then(res => {
          this.clickGroup(this.curGroupId)
        })
    },
    addProblem() {

    }
  },
  created() {
    // groups
    this.$store.dispatch(`GET_GROUPS`)
      .then(res => {
        if(this.$store.state.groups.length > 0){
          this.clickGroup(this.$store.state.groups[0].groupId)
        }
      })
    
    // invitations
    this.$store.dispatch(`GET_INVITATIONS`)

    // 
  },
  components: {
    Table
  }
};
</script>

