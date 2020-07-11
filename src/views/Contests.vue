<style scoped>
.nav {
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
  <div>
    <h1>
      {{ groupName }}
    </h1>
    <div class="nav">
      <span
        v-on:click="goto('ContestList')"
        v-bind:class="getButtonClass('ContestList')"
        >연습</span>
      <span
        v-on:click="goto('ContestCreate')"
        v-bind:class="getButtonClass('ContestCreate')"
        >연습만들기</span>
      <span
        v-on:click="goto('GroupMembers')"
        v-bind:class="getButtonClass('GroupMembers')"
        >멤버</span>
    </div>
    <router-view></router-view>
  </div>
</template>
<script>
/* eslint-disable eqeqeq */
import { get } from '@/components/util.js'
export default {
  data () {
    return {
      groupName: ''
    }
  },
  methods: {
    goto (name) {
      this.$router.push({
        name,
        params: {
          groupId: this.$route.params.groupId
        }
      })
    },
    getButtonClass (name) {
      if (name == this.$route.name) {
        return 'clicked'
      }
      return 'unclicked'
    }
  },
  async created () {
    let res = await get(`api/groups/${this.$route.params.groupId}`)
    this.groupName = res.data.data.groupName
  }
}
</script>
