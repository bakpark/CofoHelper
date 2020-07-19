<style>

</style>
<template>
    <div>
        <div v-for="(contest, index) in contests" :key="index">
            <button v-on:click="click(contest.contestId)">{{contest.contestName}}</button>
        </div>
    </div>
</template>
<script>
import { get } from '@/components/util.js'
export default {
  data () {
    return {
      contests: []
    }
  },
  async created () {
    let res = await get(`/api/groups/${this.$route.params.groupId}/contests`)
    this.contests = res.data.data
  },
  methods: {
    click: function (contestId) {
      this.$router.push({
        path: `/groups/${this.$route.params.groupId}/contests/${contestId}`
      })
    }
  }
}
</script>
