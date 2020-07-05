<template>
  <div class="total">
    <div class="main_view">
      <div class="elements_container">
        <div class="groups_header">그룹이름</div>
        <div
          v-for="(group, index) in groups"
          :key="index"
          v-on:click="clickGroup(group.groupId)"
        >
          {{ group.groupName }}
        </div>
      </div>
      <div class="elements_container">
        <div class="groups_header">Contest이름</div>
        <div
          v-for="(contest, index) in contests"
          :key="index"
          v-on:click="clickContest(contest.contestId)"
        >
          {{ contest.contestName }}
        </div>
      </div>
      <div class="table_container">
        <Table
            v-for="(contestInfo, index) in contestInfos"
            :key="index"
            :columns="contestInfo.columns"
            :rows="contestInfo.rows"
            :contestId="Number(contestInfo.contestId)"
          ></Table>
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
      groups: [],
      contests: [],
      curGroupId: 0,
      curContestId: 0,
      contestInfos: []
    };
  },
  methods: {
    clickGroup(groupId) {
      this.$axios
        .get(`api/groups/${groupId}/contests`, {
          headers: {
            authorization: localStorage.getItem("authorization").toString()
          }
        })
        .then(res => {
          this.contests = res.data.data;
        });
      this.curGroupId = groupId;
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
    }
  },
  created() {
    // 내 groups를 호출한다
    this.$axios
      .get(`api/users/${this.$store.state.handle}/groups`, {
        headers: {
          authorization: localStorage.getItem("authorization").toString()
        }
      })
      .then(res => {
        this.groups = res.data.data;
      });
  },
  components: {
    Table
  }
};
</script>
<style>
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
