<template>
  <div id="app">
    <div class="nav_container" v-if="$store.state.isLoggedIn&&$store.state.startLoadComplete">
      <div class="logo_container">
        <div class="logo" v-on:click="()=>{$router.push({path:'/'})}">COFO HELPER</div> <p>🍺🍺</p>
      </div>
      <div class="menus">
        <button v-on:click="() => $router.push({ path: '/contest/1369/B' })">
          풀이방⚡
        </button>
        <button v-on:click="() => $router.push({ path: '/' })">현황🏆</button>
        <button v-on:click="() => $router.push({ path: '/' })">
          연습만들기✍
        </button>
        <button v-on:click="() => $router.push('/realtime')">
          실시간제출현황⏳
        </button>
      </div>
      <br />
      <br />
    </div>
    <router-view/>
    <div class="assignment_window" v-if="$store.state.isLoggedIn&&$store.state.startLoadComplete">
      <div>
        <span>이번주 과제</span>
        <Table
          v-for="(contestInfo, index) in contestsInfo"
          :key="index"
          :columns="contestInfo.columns"
          :rows="contestInfo.rows"
          :contestId="contestInfo.contestId"
        ></Table>
      </div>
    </div>
    <div></div>
  </div>
</template>
<script>
/* eslint-disable vue/no-parsing-error */
/* eslint-disable */
import Table from "@/components/Table.vue";
import axios from "axios";
import lodash from "lodash";
import util from "@/components/util.js";
export default {
  name: "App",
  methods: {
    goto(pageName) {
      this.$router.push(pageName);
    }
  },
  data() {
    return {
      submissions: []
    };
  },
  computed: {
    contestsInfo() {
      const members = this.$store.state.members;
      const ourContestIds = this.$store.state.contests;
      const problemIndexes = ["A", "B", "C", "D", "E", "F"];

      // submissionDic[1369]['A']['bakpark'] = [제출기록1, 제출기록2, ....];
      const submissionDic = {};
      ourContestIds.forEach(contestId => {
        submissionDic[contestId] = {};
        problemIndexes.forEach(problemIndex => {
          submissionDic[contestId][problemIndex] = {};
          members.forEach(member => {
            submissionDic[contestId][problemIndex][member] = [];
          });
        });
      });

      this.submissions
        .filter(submission => {
          let contestId = submission.contestId;
          let prIndex = submission.problem.index;
          let member = submission.author.members[0].handle;
          return (
            submissionDic[contestId] != undefined &&
            submissionDic[contestId][prIndex] != undefined &&
            submissionDic[contestId][prIndex][member] != undefined
          );
        })
        .forEach(submission => {
          let contestId = submission.contestId;
          let prIndex = submission.problem.index;
          let member = submission.author.members[0].handle;
          submissionDic[contestId][prIndex][member].push(submission);
        });

      let contestsInfo = [];
      ourContestIds.forEach(contest => {
        let tempRows = {};
        members.forEach(member => {
          tempRows[member] = {
            name: member
          };
        });

        members.forEach(member => {
          problemIndexes.forEach(problemIndex => {
            tempRows[member][problemIndex] = {
              result: "Empty",
              notes: "",
              submission_url: ""
            };

            let tmpSubmissions = submissionDic[contest][problemIndex][member];
            // result
            if (tmpSubmissions.some(subm => subm.verdict == "OK")) {
              tempRows[member][problemIndex].result = "OK";
            } else if (tmpSubmissions.length == 0) {
              tempRows[member][problemIndex].result = "";
            } else {
              tempRows[member][problemIndex].result = tmpSubmissions.length - 1;
            }

            // notes
            let notes = tmpSubmissions.map(subm => ({
              result: subm.verdict,
              time: util.transformUnixTime(subm.creationTimeSeconds * 1)
            }));
            tempRows[member][problemIndex].notes = notes;

            //submission_url
            // 마지막 서브미션의 url을 보여줘야 함.
            if (tmpSubmissions.length != 0) {
              let submission_url =
                "http://codeforces.com/contest/" +
                tmpSubmissions[0].problem.contestId +
                "/submission/" +
                tmpSubmissions[0].id;
              tempRows[member][problemIndex].submission_url = submission_url;
            }
          });
        });

        let contestInfo = {
          columns: ["name", "A", "B", "C", "D", "E", "F"],
          rows: Object.values(tempRows),
          contestId: contest
        };
        contestsInfo.push(contestInfo);
      });
      return contestsInfo;
    }
  },
  components: {
    Table
  },
  created() {
    const members = this.$store.state.members;
    const contests = this.$store.state.contests;
    // contests => contest 하나당 테이블 하나 생성
    members.forEach(member => {
      this.$axios
        .get("/user.status", {
          params: {
            handle: member,
            from: 1,
            count: 50
          }
        })
        .then(res => {
          this.submissions = this.submissions.concat(res.data.result);
          console.log(this.submissions);
        });
    });
  }
};
</script>

<style>
#app {
  display: flex;
  flex-direction: column;
  align-items: center;
}
.assignment_window {
  position: fixed;
  right: 5vw;
  top: 5vh;

  display: flex;
  flex-direction: column;
  align-items: center;
}

.nav_container {
  background-color: white;
  width: 100%;
  display: flex;
  justify-content: flex-start;
  align-items: flex-end;
  border-bottom: 1px solid rgb(211, 211, 211);
}
.logo_container {
  display: flex;
  align-items: center;
  cursor: pointer;
}
.nav_container .logo_container .logo {
  margin-left: 5vw;
  font-size: 3em;
  font-weight: 800;
  color: rgb(166, 187, 255);
  height: 100%;
}
.nav_container p {
  font-size: 3em;
  font-weight: 800;
  padding: 15px;
}
.menus {
  margin-left: 3vw;
  margin-top: 10px;
  margin-bottom: 10px;
}
.nav_container .menus button {
  border: 0px;
  padding: 5px;
  margin-right: 10px;
  font-size: 1.3em;
  font-weight: 800;
  border-radius: 10px;
  background-color: rgb(199, 223, 253);
  color: rgb(43, 59, 151);
}
.nav_container .menus button:hover{
  opacity: 0.7;
  outline: none;
}
</style>
