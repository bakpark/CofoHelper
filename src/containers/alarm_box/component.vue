<template>
    <div class="container_relative">
      <div class="container_absolute">
        <div class="row"
          v-for="(invitation, index) in $store.state.invitations"
          :key="index">{{invitation.inviter.handle}}로부터 {{invitation.group.groupId}}그룹 초대 도착
            <button v-on:click="clickNo(invitation.invitationId)">NO</button>
            <button v-on:click="clickYes(invitation.invitationId)">수락</button>
          </div>
        </div>
    </div>
</template>
<script>
export default {
  props: {
    invitations: Array
  },
  methods: {
    clickNo (invitationId) {
      console.log(`gsgsd`)
      this.$store.dispatch('NO_INVITATION', invitationId)
    },
    clickYes (invitationId) {
      this.$store.dispatch('ACCEPT_INVITATION', invitationId)
    }
  }
}
</script>
<style>
.container_relative{
  position: relative;
}
.container_absolute{
    position: absolute;
    display: flex;
    flex-direction: column;
    align-items: center;
    z-index: 10;
}
.container .row{

}
</style>
