<template>
  <div class="container">
    <i class="fas fa-cog blue" v-if="clicked" v-on:click="click"></i>
    <i class="fas fa-cog" v-else v-on:click="click"></i>
    <UserSettingsComponent
      v-show="clicked"
    ></UserSettingsComponent>
  </div>
</template>
<script>
/* eslint-disable eqeqeq */
import UserSettingsComponent from './component.vue'
export default {
  components: {
    UserSettingsComponent
  },
  data () {
    return {
      clicked: false
    }
  },
  methods: {
    click () {
      this.clicked = !this.clicked
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
  padding: 0.3em;
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
