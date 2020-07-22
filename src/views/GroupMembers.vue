<style>

</style>
<template>
  <div>
      <div>
          <div v-for="(member, index) in members" :key="index">{{member.handle}}</div>
      </div>
      <div v-if="addFlag">
            <input type="text" v-model="newGroupMember" />
            <button v-on:click="inviteMember">추가</button>
      </div>
      <div><button @click="addFlagOn()">+</button></div>
  </div>
</template>
<script>
import { get, post } from '@/components/util.js'
export default {
  data () {
    return {
      members: [],
      addFlag: false,
      newGroupMember: ''
    }
  },
  created: async function () {
    let res = await get(`/api/groups/${this.$route.params.groupId}/users`)
    this.members = res.data.data
  },
  methods: {
    addFlagOn () {
      this.addFlag = true
    },
    inviteMember: async function (callee) {
      let member = this.newGroupMember
      if (this.members.includes(member)) {
        alert('이미 추가된 멤버 입니다')
        return null
      }
      if (member === this.$store.state.handle) {
        alert('자기 자신은 추가할 수 없습니다')
        return null
      }
      let res = await get(`/api/users/${member}`)
      if (res.data.data === false) {
        alert('해당 유저는 존재하지 않습니다')
        return null
      }

      let inviteRes = await post('/api/invitations', {
        groupId: this.$route.params.groupId,
        invitedHandle: member
      })
      if (inviteRes.data.status !== 200) {
        alert('invitation error')
        console.error(inviteRes.data.message)
        return null
      }
      this.addFlag = false
      this.newGroupMember = ''
      alert(member + '을 초대했습니다.')
    }
  }
}
</script>
