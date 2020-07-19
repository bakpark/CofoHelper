<style>

</style>
<template>
    <div>
        <div v-for="(member, index) in members" :key="index">{{member.handle}}</div>
    </div>
</template>
<script>
import { get } from '@/components/util.js'
export default {
  data () {
    return {
      members: []
    }
  },
  created: async function () {
    let res = await get(`/api/groups/${this.$route.params.groupId}/users`)
    this.members = res.data.data
  }
}
</script>
