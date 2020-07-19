<template>
  <div class="group-submits">
    <div class="table-wrapper" >
      <ul>
        <li
          v-for="obj in members"
          :key="obj.handle">
          <SubmitTable :handle="obj.handle"></SubmitTable>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
/*****************************************************************
 ************************** import *******************************
 *****************************************************************/
import SubmitTable from '@/components/SubmitTable.vue'
export default {
  name: 'GroupSubmits',
  components: {
    SubmitTable: SubmitTable
  },
  props: {
    group: Object
  },
  data () {
    return {
      members: []
    }
  },
  /*****************************************************************
   ********************** computed, watch ***********************
   *****************************************************************/
  computed: {
  },
  watch: {
    group (changed) {
      this.getMembers(changed.groupId)
    }
  },
  /*****************************************************************
   ************************** Life-Cycle ***************************
   *****************************************************************/
  created () {
    if (this.group.groupId) {
      this.getMembers(this.group.groupId)
    }
  },
  mounted () {
  },
  beforeDestroy () {
  },
  destroyed () {
  },
  /*****************************************************************
   ********************** User-Defined Methods *********************
   *****************************************************************/
  methods: {
    getMembers (groupId) {
      this.members = []
      this.$axios.get(`api/groups/${groupId}/users`, {
        headers: { authorization: localStorage.getItem('authorization').toString() }
      }).then(res => {
        this.members = res.data.data
      }).catch(err => {
        console.error(err)
      })
    }
  }
}
</script>

<style scoped>
.group-submits{
  height: 10em;
}
.table-wrapper {
  width: 90%;
  height: 100%;
  display: flex;
  flex-direction: row;
}
.table-wrapper ul {
  width: 100%;
}
.table-wrapper ul li {
  width: 33.3333%;
  line-height: 2em;
  float: left;
}
</style>
