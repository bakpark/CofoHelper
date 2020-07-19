<style></style>
<template>
  <div>
    <h1>Contest 만들기</h1>
    <span>Contest 이름:</span><input type="text" v-model="newContestName" />
    <div> 끝나는시간:<datetime
        type="datetime"
        v-model="newContestEndtime"
        use12-hour>{{ $moment().format("MM월-dd일 HH시 mm분") }}</datetime>
    </div>
    <button v-on:click="makeNewContest">만들기</button>
  </div>
</template>
<script>
import { post } from '@/components/util.js'
export default {
  data () {
    return {
      groupId: this.$route.params.groupId,
      newContestName: '',
      newContestEndtime: this.$moment().format("yyyy-MM-dd'T'HH:mm:ss")
    }
  },
  methods: {
    makeNewContest: async function () {
      let res = await post(`api/groups/${this.groupId}/contests`, {
        contestName: this.newContestName,
        endTime: this.newContestEndtime.split('.')[0]
      })
      if (res.status === 200) {
        let { contestId } = res.data.data
        this.$router.push({
          path: `/groups/${this.$route.params.groupId}/contests/${contestId}`
        })
      }
    },
    initializeInputs: function () {
      this.newContestName = ''
      this.newContestEndtime = ''
    }
  }
}
</script>
