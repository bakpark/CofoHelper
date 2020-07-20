<style>

</style>
<template>
  <div>
      <div>
          <div v-for="(member, index) in members" :key="index">{{member.handle}}</div>
      </div>
      <div v-if="addFlag">
            <input type="text" v-model="newGroupMember" />
            <button v-on:click="addMember">추가</button>
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
    let This = this
    let res = await get(`/api/groups/${This.$route.params.groupId}/users`)
    This.members = res.data.data
    console.log('memebrs====>', This.members)
  },
  methods: {
    addFlagOn () {
      this.addFlag = true
    },
    addMember: async function (callee) {
      let This = this
      let member = this.newGroupMember
      if (this.members.includes(member)) {
        alert('이미 추가된 멤버 입니다')
        return null
      }
      if (member === this.$store.state.handle) {
        alert('자기 자신은 추가할 수 없습니다')
        return null
      }
      let res = await get(`api/users/${member}`)
      if (res.data.data === false) {
        alert('해당 유저는 존재하지 않습니다')
        return null
      }
      let addMemberRes = await post(`/api/groups/${This.$route.params.groupId}/addMember`, { handle: member })
      if (addMemberRes.data.status !== 200) { return null }

      await This.members.push(member)
      let inviteRes = await post('api/invitations', {
        groupId: addMemberRes.data.data.group.groupId,
        invitedHandle: member
      })
      if (!inviteRes.data.data) {
        alert('invitation error')
        return null
      }
      This.members.add(member)
      This.addFlag = false
      This.newGroupMember = ''
    }
  }
}
</script>
