<style scoped>
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
  margin-bottom: 2em;
}
.group-button {
  margin: 0.3em;
  height: 3em;
  width: 8em;
}
button.hoverable_btn_group {
  color: rgb(248, 255, 255);
  background-color: #337882;
  height: 2em;
  width: 5em;
  font-size: 2em;
  font-weight: 600;
  border-radius: 10px;
  border: none;
  margin: 3px;
}
button.hoverable_btn_group:hover {
  border: 2px solid #337882;
  background-color:rgb(248, 255, 255);
  color: #337882;
}
</style>
<template>
  <div class="realtime-solve">
    <div class="body-wrapper">
      <div class="button-wrapper">
        <button class="hoverable_btn_group" v-for="group in $store.state.groups" :key="group.groupName" @click="selectGroup(group)"> {{ group.groupName }}</button>
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
