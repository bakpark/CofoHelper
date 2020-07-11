<template>
  <div class="container">
    <div class="alarm_count" v-if="$store.state.invitations.length>0">{{$store.state.invitations.length}}</div>
    <i class="fas fa-bell blue" v-if="clicked" v-on:click="click"></i>
    <i class="fas fa-bell" v-else v-on:click="click"></i>
    <AlarmBoxComponent
      v-show="clicked"
      :invitations="$store.state.invitations"
    ></AlarmBoxComponent>
  </div>
</template>
<script>
/* eslint-disable eqeqeq */
import AlarmBoxComponent from './component.vue'
export default {
  components: {
    AlarmBoxComponent
  },
  data () {
    return {
      clicked: false
    }
  },
  methods: {
    click () {
      this.clicked = !this.clicked
      if (this.clicked == true) {
        this.$store.dispatch('GET_INVITATIONS')
      }
    }
  }
}
</script>
<style scoped>
.container {
  position:relative;
  display: flex;
  flex-direction: column;
  align-items: center;
}
.blue {
  color: blue;
}
.container .alarm_count{
  position:absolute;
  color: red;
  font-weight: 800;
  bottom:10px;
  left:10px;
}
</style>
