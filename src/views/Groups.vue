<style scoped>
.groups_container {
  margin-top: 5vh;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  width: 60%;
  height: 100%;
}
.nav {
  margin-top: 5vh;
  display: flex;
  justify-content: flex-start;
  margin-bottom: 15px;
}
.nav span{
  font-size: 1em;
  font-weight: 600;
  padding: 10px 15px;
}
.nav .unclicked {
  background-color: white;
  color: black;
}
.nav .unclicked:hover {
  background-color: rgb(197, 197, 197);
  color: blue;
}
.nav .clicked {
  background-color: blue;
  color: white;
}
</style>
<template>
  <div class="groups_container">
    <div class="nav">
      <span
        v-on:click="goto('GroupList')"
        v-bind:class="getButtonClass('GroupList')"
        >그룹</span>
      <span
        v-on:click="goto('GroupCreate')"
        v-bind:class="getButtonClass('GroupCreate')"
        >그룹 만들기</span>
    </div>
    <router-view></router-view>
  </div>
</template>
<script>
/* eslint-disable eqeqeq */
export default {
  data () {
    return {
      groups: [],
      newGroupName: ''
    }
  },
  methods: {
    goto (name) {
      this.$router.push({
        name
      })
    },
    getButtonClass (name) {
      if (name == this.$route.name) {
        return 'clicked'
      }
      return 'unclicked'
    }
  }
}
</script>
