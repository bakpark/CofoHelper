<style scoped>
body {
  width: 100vw;
  height: 100vh;
}
#app {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
}
.main_view {
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: center;
  width: 100%;
  height: 100%;
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

@media (min-width: 320px) and (max-width: 480px) {
  .nav_container {
    background-color: white;
    width: 100%;
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    align-items: flex-start;
    border-bottom: 1px solid rgb(211, 211, 211);
    padding-bottom: 8px;
  }
  .nav_container .logo_container .logo {
    margin-left: 5vw;
    font-size: 2em;
    font-weight: 800;
    color: rgb(166, 187, 255);
    height: 100%;
  }
  .nav_container p {
    font-size: 2em;
    font-weight: 800;
    padding: 5px;
  }
  .nav_container .menus {
    width: 100%;
    display: flex;
    justify-content: space-around;
  }
  .nav_container .menus .not_personal_menus{
    display: flex;
    justify-content: flex-start;
    align-content: center;
    margin-left: 3vw;
    margin-top: 3px;
    margin-bottom: 2px;
  }
  .nav_container .menus button {
    border: 0px;
    padding: 5px;
    margin-right: 5px;
    font-size: 0.8em;
    font-weight: 800;
    border-radius: 4px;
    background-color: rgb(199, 223, 253);
    color: rgb(43, 59, 151);
  }
}
@media (min-width: 1000px) {
  .nav_container {
    background-color: white;
    width: 100%;
    display: flex;
    justify-content: flex-start;
    align-items: flex-end;
    border-bottom: 1px solid rgb(211, 211, 211);
    padding-bottom: 14px;
  }
  .nav_container .logo_container .logo {
    margin-left: 5vw;
    font-size: 2em;
    font-weight: 800;
    color: rgb(166, 187, 255);
    height: 100%;
  }
  .nav_container p {
    font-size: 2em;
    font-weight: 800;
    padding: 5px;
  }
  .nav_container .personal_menus strong{
    margin-left:10vw;
  }
  .nav_container .logo_container{
    width: 30%;
  }
  .nav_container .menus {
    width: 70%;
    display: flex;
  }
  .nav_container .menus .not_personal_menus{
    width: 50%;
    display: flex;
    justify-content: flex-start;
    align-content: center;
    margin-left: 3vw;
    margin-top: 3px;
    margin-bottom: 2px;
  }
  .nav_container .menus .personal_menus{
    width: 50%;
    margin-right: 10px;
    display: flex;
    justify-content: flex-end;
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
}
.logo_container {
  display: flex;
  align-items: center;
  cursor: pointer;
}
.nav_container .menus button:hover {
  opacity: 0.7;
  outline: none;
}
.icon_box {
  display: flex;
  flex-direction: row;
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
        <div class="not_personal_menus">
          <button v-on:click="() => $router.push({ path: '/contest/1/1' })">
            풀이방⚡
          </button>
          <button v-on:click="() => $router.push({ path: '/groups/list' })">
            그룹🏆
          </button>
          <button v-on:click="() => $router.push('/realtime')">
            실시간제출현황⏳
          </button>
        </div>
        <div class="personal_menus">
          <strong>{{ $store.state.handle }}</strong>
          <div class="icon_box">
            <UserSettings></UserSettings>
            <AlarmBox></AlarmBox>
          </div>
        </div>
      </div>
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
import UserSettings from "@/containers/user_settings"
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
    Table,
    AlarmBox,
    UserSettings
  }
};
</script>
