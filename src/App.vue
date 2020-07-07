
<style scoped>
#app {
  display: flex;
  flex-direction: column;
  align-items: center;
}
.main_view{
  display:flex;
}
.right_real_toggle {
  position: fixed;
  top: 2vh;
  right: 3vw;
}
.assignment_window {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.nav_container {
  background-color: white;
  width: 100%;
  display: flex;
  justify-content: flex-start;
  align-items: flex-end;
  border-bottom: 1px solid rgb(211, 211, 211);
}
.logo_container {
  display: flex;
  align-items: center;
  cursor: pointer;
}
.nav_container .logo_container .logo {
  margin-left: 5vw;
  font-size: 3em;
  font-weight: 800;
  color: rgb(166, 187, 255);
  height: 100%;
}
.nav_container p {
  font-size: 3em;
  font-weight: 800;
  padding: 15px;
}
.menus {
  display:flex;
  justify-content: flex-start;
  align-content: center;
  margin-left: 3vw;
  margin-top: 10px;
  margin-bottom: 10px;
}
.nav_container .menus button {
  border: 0px;
  padding: 5px;
  margin-right: 10px;
  font-size: 1.3em;
  font-weight: 800;
  border-radius: 10px;
  background-color: rgb(199, 223, 253);
  color: rgb(43, 59, 151);
}
.nav_container .menus button:hover {
  opacity: 0.7;
  outline: none;
}
</style>

<template>
  <div id="app">
    <div
      class="nav_container"
      v-if="$store.state.isLoggedIn && $store.state.startLoadComplete"
    >
      <div class="logo_container">
        <div
          class="logo"
          v-on:click="
            () => {
              $router.push({ path: '/' });
            }
          "
        >
          COFO HELPER
        </div>
        <p>🍺🍺</p>
      </div>
      <div class="menus">
        <button v-on:click="() => $router.push({ path: '/contest/1/1' })">
          풀이방⚡
        </button>
        <button v-on:click="() => $router.push({ path: '/' })">그룹🏆</button>
        <button v-on:click="() => $router.push({ path: '/' })">
          연습만들기✍
        </button>
        <button v-on:click="() => $router.push('/realtime')">
          실시간제출현황⏳
        </button>
        <strong style="margin-left:10vw;">{{$store.state.handle}}</strong>
        <AlarmBox></AlarmBox>
      </div>
      <br />
      <br />
    </div>
    <div class="main_view">
      <router-view />
    </div>
  </div>
</template>
<script>
/* eslint-disable vue/no-parsing-error */
/* eslint-disable */
import Table from "@/components/Table.vue";
import axios from "axios";
import lodash from "lodash";
import util from "@/components/util.js";
import AlarmBox from "@/containers/alarm_box";
export default {
  name: "App",
  methods: {
    goto(pageName) {
      this.$router.push(pageName);
    }
  },
  data() {
    return {
      submissions: []
    };
  },
  components: {
    Table, AlarmBox
  },
};
</script>
