<template>
  <div class="realtime-solve">
    <div class="body-wrapper">
      <div class="button-wrapper">
        <button class="group-button" v-for="group in $store.state.groups" :key="group.groupName" @click="selectGroup(group)"> {{ group.groupName }}</button>
      </div>
    </div>
    <GroupSubmits :group="selectedGroup"></GroupSubmits>
  </div>
</template>
<script>
import GroupSubmits from '@/components/GroupSubmits.vue'
export default {
  name: 'RealTimeSolve',
  components: {
    GroupSubmits: GroupSubmits
  },
  props: {
  },
  data () {
    return {
      selectedGroup: {}
    }
  },
  created () {
    this.$store.dispatch(`GET_GROUPS`).then(() => {
      if (this.$store.state.groups.length > 0) {
        this.selectedGroup = this.$store.state.groups[0]
      }
    })
  },
  methods: {
    selectGroup (group) {
      this.selectedGroup = group
    }
  }
}
</script>
<style>
.realtime-solve {
  width: 100%;
}
.body-wrapper {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
}
.button-wrapper {
  width: 90%;
  margin-top: 2em;
  display: flex;
}
.group-button {
  margin: 0.3em;
  height: 3em;
  width: 8em;
}
</style>
