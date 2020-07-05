<template>
  <div class="total">
    <div class="left_view"></div>
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
    </div>
  </div>
</template>
<script>
/* eslint-disable */
import Table from "@/components/Table.vue";
import lodash from "lodash";
import util from "@/components/util.js";
import constants from "@/common/constants.js";

export default {
  data() {
    return {
      groups: [],
      contests: [],
      curGroupId: 0,
      curContestId: 0
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
      .then(res=> {
        this.contests = res.data.data;
      })
    },
    clickContest(contestId) {}
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

.left_view {
  width: 6vw;
  display: flex;
  flex-direction: column;
  align-items: center;
  height: 40vh;
  background-color: gray;
}
.left_view .practices_header {
  font-size: 1.5em;
  font-weight: 600;
}
.left_view .practices {
  display: flex;
  flex-direction: column;
  align-items: center;
}
</style>
